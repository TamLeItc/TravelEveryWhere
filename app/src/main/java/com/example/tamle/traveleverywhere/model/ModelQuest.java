package com.example.tamle.traveleverywhere.model;

import android.os.AsyncTask;

import com.example.tamle.traveleverywhere.presenter.quest.PresenterImpHandleQuest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tamle on 08/04/2018.
 */

public class ModelQuest {

    private PresenterImpHandleQuest mPresenterHandleQuest;

    public ModelQuest(PresenterImpHandleQuest presenterHandleQuest){
        this.mPresenterHandleQuest = presenterHandleQuest;
    }

    public void loadData(String quest){
        StringBuilder input = new StringBuilder(quest);

        //đường link ko thể tồn tại kí tự cách, phải thay thế
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == ' '){
                input.replace(i, i + 1, "%20");
            }
        }

        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                + input.toString() + "&types=geocode&language=en&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

        ReadDataPlace loadDataPlace = new ReadDataPlace();
        loadDataPlace.execute(url);
    }

    public class ReadDataPlace extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {

            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                return content.toString();

            } catch (MalformedURLException e) {
                mPresenterHandleQuest.OnLoadDataQuestFailed(e.toString());
            } catch (IOException e) {
                mPresenterHandleQuest.OnLoadDataQuestFailed(e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mPresenterHandleQuest.OnLoadDataQuestSuccessful(s);

        }
    }

}
