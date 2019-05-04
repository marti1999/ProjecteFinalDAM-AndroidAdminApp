package com.example.marti.projecte_uf1;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Classification;
import com.example.marti.projecte_uf1.model.Color;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Gender;
import com.example.marti.projecte_uf1.model.PersoCloth;
import com.example.marti.projecte_uf1.model.Size;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentQR extends Fragment {

    private static final int MAX_EACH_TIME = 6;

    public FragmentQR() {
    }

    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private ApiMecAroundInterfaces mAPIService;

    private Button generate_QRCode;
    private Button btnAddCloth;
    private ImageView qrCode;
    private TextView actualClothes;
    private android.support.design.widget.TextInputEditText etQnt;
    private de.hdodenhof.circleimageview.CircleImageView btnDelCloth;
    private Spinner clothClassification;
    private Spinner clothColor;
    private Spinner clothSize;
    private Spinner clothGender;
    private List<PersoCloth> qrCloth;
    private Donor donorActual;
    private int donorId = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        viewFormBindings(view);
        mAPIService = ApiUtils.getAPIService();
        qrCloth = new ArrayList<>();
        setActualDonor();
        setActualCloths();
        fillSpinners();
        btnAddCloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCloth();
            }
        });

        generate_QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCloth.size() > 0) {
                    String finalText = "";
                    int clothGivenFinal = donorActual.ammountGiven;
                    int currDonorPoints = donorActual.points;
                    finalText += "{'NumGiven': "+clothGivenFinal +", 'DonorCurrPoints':"+currDonorPoints+", 'CustomCloths':[";
                    for (PersoCloth p :qrCloth) {
                        finalText += p.toString() + ",";
                    }
                    finalText = finalText.substring(0,finalText.length()-1);
                    finalText += "]}";

                    generateQR(finalText);
                }else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelCloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanPersoClothList();
            }
        });
        return view;
    }

    private void cleanPersoClothList() {
        qrCloth.clear();
        setActualCloths();
    }

    private void setActualCloths() {
        String clothText = getString(R.string.base_total_clothes)+ " " + qrCloth.size();
        actualClothes.setText(clothText);
    }

    private void generateQR(String finalText) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(finalText, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void setActualDonor() {
        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        try {
            donorId = Integer.parseInt(prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "0"));
        } catch (Exception e) {
            Log.d("Donor id get", e.toString());
        }
        mAPIService.getDonorById(donorId).enqueue(new Callback<Donor>() {
            @Override
            public void onResponse(Call<Donor> call, Response<Donor> response) {
                if (response.isSuccessful()) {


                    if (response.body() != null) {
                        donorActual = response.body();
                    } else {
                        Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Donor> call, Throwable t) {
                Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fillSpinners() {
        setSizeSpinner();
        setGenderSpinner();
        setColorSpinner();
        setClassificationSpinner();
    }

    private void setClassificationSpinner() {
        mAPIService.getClothClassifications().enqueue(new Callback<List<Classification>>() {
            @Override
            public void onResponse(Call<List<Classification>> call, Response<List<Classification>> response) {
                if (response.isSuccessful()) {
                    List<Classification> list = response.body();
                    ArrayAdapter<Classification> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    clothClassification.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Classification>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setColorSpinner() {
        mAPIService.getClothColors().enqueue(new Callback<List<Color>>() {
            @Override
            public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                if (response.isSuccessful()) {
                    List<Color> list = response.body();
                    ArrayAdapter<Color> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    clothColor.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Color>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGenderSpinner() {
        mAPIService.getClothGenders().enqueue(new Callback<List<Gender>>() {
            @Override
            public void onResponse(Call<List<Gender>> call, Response<List<Gender>> response) {
                if (response.isSuccessful()) {
                    List<Gender> list = response.body();
                    ArrayAdapter<Gender> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    clothGender.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Gender>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSizeSpinner() {
        mAPIService.getClothSizes().enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                if (response.isSuccessful()) {
                    List<Size> list = response.body();
                    ArrayAdapter<Size> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    clothSize.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCloth() {

        String qntFinalText = etQnt.getText().toString();
        if (qntFinalText.equals("") || qntFinalText.equals("0")) {
            qntFinalText = "1";
        }
        int qntFinal = Integer.parseInt(qntFinalText);
        if(isValidQuantity(qntFinal) && isValidQuantity(qrCloth.size()+qntFinal)) {

            if (donorId != 0) {
                Classification classif = (Classification) clothClassification.getSelectedItem();
                int idClassification = classif.id;
                int clothPoints = classif.value;
                Color col = (Color) clothColor.getSelectedItem();
                int idColor = col.id;
                Size s = (Size) clothSize.getSelectedItem();
                int idSize = s.id;
                Gender gen = (Gender) clothGender.getSelectedItem();
                int idGender = gen.id;


                PersoCloth persoCloth = new PersoCloth(donorId, idClassification, idColor, idSize, idGender, clothPoints);

                addCustomClothToList(persoCloth, qntFinal);
                setActualCloths();
            } else {
                Toast.makeText(getActivity(), "An ERROR has been occurred generating the QR", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "The max quantity of items (each time) is "+MAX_EACH_TIME, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidQuantity(int qnt) {
        return qnt > 0 && qnt <= MAX_EACH_TIME;
    }

    private void addCustomClothToList(PersoCloth persoCloth, int qntFinal) {
        for (int i = 0; i < qntFinal; i++) {
            qrCloth.add(persoCloth);
        }
    }

    private void viewFormBindings(View view) {
        generate_QRCode = (Button) view.findViewById(R.id.generate_qr);
        btnAddCloth = (Button) view.findViewById(R.id.addItemToList);
        btnDelCloth = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.deleteClothes);
        qrCode = (ImageView) view.findViewById(R.id.imageView);
        etQnt = (android.support.design.widget.TextInputEditText) view.findViewById(R.id.etClothQnt);
        actualClothes = (TextView) view.findViewById(R.id.totalClothes);


        clothClassification = (Spinner) view.findViewById(R.id.spinClothType);
        clothColor = (Spinner) view.findViewById(R.id.spinClothColor);
        clothSize = (Spinner) view.findViewById(R.id.spinClothSize);
        clothGender = (Spinner) view.findViewById(R.id.spinClothGender);
    }
}
