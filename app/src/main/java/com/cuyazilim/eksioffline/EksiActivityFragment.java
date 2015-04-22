package com.cuyazilim.eksioffline;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * A placeholder fragment containing a simple view.
 */
public class EksiActivityFragment extends Fragment {
   static ArrayList<String> entryList=new ArrayList<>();
    static ArrayList<String> authorList=new ArrayList<>();
    static ArrayList<String> favList=new ArrayList<>();
    public EksiActivityFragment() {
    }
    static ListView lst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return
                inflater.inflate(R.layout.fragment_eksi, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        lst=(ListView) view.findViewById(R.id.listView);
        
        new LoadXML().execute("http://www.cuyazilim.com/cantest/eksi.php?q=");
    }
    public class LoadXML extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPostExecute(Void aVoid) {

            lst.setAdapter(new MyCustomAdapter(getActivity(),R.layout.activity_eksi,entryList));

            dialog.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Yükleniyor");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            //  d = params[0];
            getDataFromXml(params[0]);
            return null;
        }
    }
    public static ArrayList<String> getDataFromXml(String s) {

        ArrayList<String> list = new ArrayList<String>();
        entryList.clear();
        authorList.clear();
        favList.clear();
        try {

            URL url = new URL(s);
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

            Document document = dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();

            NodeList nodeListCountry = document.getElementsByTagName("item");
            for (int i = 0; i < nodeListCountry.getLength(); i++) {
                Node node = nodeListCountry.item(i);
                Element elementMain = (Element) node;

                NodeList nodeListText = elementMain.getElementsByTagName("author");
                Element elementText = (Element) nodeListText.item(0);

                NodeList nodeListText1 = elementMain.getElementsByTagName("entry");
                Element elementText1 = (Element) nodeListText1.item(0);

                if (elementText.getChildNodes().getLength() > 0) {
                    authorList.add(elementText.getChildNodes().item(0).getNodeValue());
                } else {
                   authorList.add(" ");
                }
                if (elementText1.getChildNodes().getLength() > 0) {
                    entryList.add(elementText1.getChildNodes().item(0).getNodeValue());

                } else {
                    entryList.add(" ");
                }
               nodeListText = elementMain.getElementsByTagName("fav");
               elementText = (Element) nodeListText.item(0);


                if (elementText.getChildNodes().getLength() > 0) {
                    favList.add(elementText.getChildNodes().item(0).getNodeValue());
                } else {
                    favList.add("0");
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }
    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,

                               ArrayList<String> wbs) {
            super(context, textViewResourceId, wbs);
            // TODO Auto-generated constructor stub

        }

        private class ViewHolder {
            public TextView entry;
            public TextView author;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);
            // final int pos;
            // pos=position;
            View row = convertView;
            final ViewHolder holder;

            if (row == null) {
                //  LayoutInflater inflater=getLayoutInflater();
                row = getActivity().getLayoutInflater().inflate(R.layout.adapter, parent, false);
                holder = new ViewHolder();
                holder.entry = (TextView) row.findViewById(R.id.textView);
                holder.author = (TextView) row.findViewById(R.id.textView2);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            Spanned spanned= Html.fromHtml(entryList.get(position));
            holder.entry.setText(spanned, TextView.BufferType.SPANNABLE);
            holder.author.setText(authorList.get(position));
            //	  ((ImageView) row.findViewById(R.id.ImgLang)).setImageResource(0);

            //    imageLoader.displayImage(xmlSImg.get(position), holder.image, options, animateFirstListener);
            return row;
        }

    }

}
