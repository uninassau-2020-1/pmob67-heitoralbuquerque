package com.example.plotarmapa;

import android.os.AsyncTask;

import com.example.plotarmapa.GoogleGeoCoding.GoogleGeoCode;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpService  extends AsyncTask<Void, Void, GoogleGeoCode> {
    private final String cep;

    public HttpService(String cep){

        this.cep = cep;

    }

    @Override
    protected GoogleGeoCode doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        StringBuilder results = new StringBuilder();

        if (this.cep != null && this.cep.length() == 8){

            try{
                URL url = new URL("http://viacep.com.br/ws/" + this.cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    resposta.append(line);
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        Cep cep = new Gson().fromJson(resposta.toString(), Cep.class);
        String logradouro = cep.getLogradouro().replace(" ", "+");
        String cidade = cep.getLocalidade().replace(" ", "+");
        String uf = cep.getUf();

        try {
            URL urlGoogleApi = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+logradouro+","+cidade+","+uf+"\n"+"&key=AIzaSyBJEkF2ocUFIxpx-6OclAXlMnfLARE3XuI");
            HttpURLConnection connection = (HttpURLConnection) urlGoogleApi.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(urlGoogleApi.openStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                results.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleGeoCode geoCode = new Gson().fromJson(results.toString(), GoogleGeoCode.class);

        return geoCode;
    }


}
