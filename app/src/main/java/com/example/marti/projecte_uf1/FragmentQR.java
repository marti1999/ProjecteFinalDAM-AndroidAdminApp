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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

    public FragmentQR() {
    }

    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private ApiMecAroundInterfaces mAPIService;

    private Button generate_QRCode;
    private Button btnAddCloth;
    private ImageView qrCode;
    private EditText etQnt;
    private Spinner clothClassification;
    private Spinner clothColor;
    private Spinner clothSize;
    private Spinner clothGender;
    private List<PersoCloth> qrCloth;
    private Donor donorActual;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        viewFormBindings(view);
        qrCloth = new ArrayList<>();

        mAPIService = ApiUtils.getAPIService();
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

                    finalText += "]}";

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(finalText, BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
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

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int donorId = 0;
        try {
            donorId = Integer.parseInt(prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "0"));
        } catch (Exception e) {
            Log.d("Donor id get", e.toString());
        }
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

            String qntFinalText = etQnt.getText().toString();
            if (qntFinalText.equals("") || qntFinalText.equals("0")) {
                qntFinalText = "1";
            }

            int qntFinal = Integer.parseInt(qntFinalText);
            PersoCloth persoCloth = new PersoCloth(donorId, idClassification, idColor, idSize, idGender, clothPoints);
            donorActual = (Donor) mAPIService.getDonorById(donorId);
            addCustomClothToList(persoCloth, qntFinal);

        } else {
            Toast.makeText(getActivity(), "An ERROR has been occurred generating the QR", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCustomClothToList(PersoCloth persoCloth, int qntFinal) {
        for (int i = 0; i < qntFinal; i++) {
            qrCloth.add(persoCloth);
        }
    }

    private void viewFormBindings(View view) {
        generate_QRCode = (Button) view.findViewById(R.id.generate_qr);
        btnAddCloth = (Button) view.findViewById(R.id.addItemToList);
        qrCode = (ImageView) view.findViewById(R.id.imageView);
        etQnt = (EditText) view.findViewById(R.id.etClothQnt);


        clothClassification = view.findViewById(R.id.spinClothType);
        clothColor = view.findViewById(R.id.spinClothColor);
        clothSize = view.findViewById(R.id.spinClothSize);
        clothGender = view.findViewById(R.id.spinClothGender);
    }
}
