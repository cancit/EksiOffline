package com.cuyazilim.eksioffline;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.text.*;
import android.text.method.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;


/**
 * A placeholder fragment containing a simple view.
 */
public class EksiActivityFragment extends Fragment {
   static ArrayList<String> entryList=new ArrayList<>();
    static ArrayList<String> authorList=new ArrayList<>();
	static ArrayList<String> dateList=new ArrayList<>();
    static ArrayList<String> favList=new ArrayList<>();
	String linkk;
	int lastSeen=0;
	View foot;
	SharedPreferences loc;
    public EksiActivityFragment() {
		setHasOptionsMenu(true);
	
    }
    static ListView lst;
	MyCustomAdapter adap;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO: Implement this method
		inflater.inflate(R.menu.menu_eksi,menu);
		super.onCreateOptionsMenu(menu, inflater);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		if(item.getItemId()==R.id.action_goentry){
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setTitle("Entry'ye Zıpla");
			alert.setMessage("Entry numarasi giriniz");
// Set an EditText view to get user input 
			final EditText input = new EditText(getActivity());
			alert.setView(input);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//	String value = input.getText();
						// Do something with value!
					
						lst.setSelection(Integer.parseInt(input.getEditableText().toString())-1);
					}
				});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
			alert.show();
		}
		if(item.getItemId()==R.id.action_ara){
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setTitle("EkşiOffline");
			alert.setMessage("başlık veya kişi girin");
// Set an EditText view to get user input 
			final EditText input = new EditText(getActivity());
			alert.setView(input);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//	String value = input.getText();
						// Do something with value!
						try
						{
							URI sa=null;
							try
							{
								sa= new URI("http", "www.cuyazilim.com", "/cantest/eksi.php", "q=" + (input.getText() + ""),null);
							}
							catch (URISyntaxException e)
							{}
							entryList.clear();
							authorList.clear();
							favList.clear();
							dateList.clear();
							EksiActivity.setSub(input.getText().toString());
							//		Toast.makeText(getActivity(),"http://www.cuyazilim.com/cantest/eksi.php?q=" + URLEncoder.encode((sa.toASCIIString().substring(44)).replaceAll(" ","+"), "UTF-8"),Toast.LENGTH_LONG).show();
							//			new LoadXML().execute(sa.toASCIIString().replaceAll("%20","+"));
							new LoadXML().execute("http://www.cuyazilim.com/cantest/eksi.php?q=" + URLEncoder.encode((sa.toASCIIString().substring(44)).replaceAll(" ","+"), "UTF-8"));
						}
						catch (UnsupportedEncodingException e)
						{}
					}
				});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
			alert.show();
		}
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return
                inflater.inflate(R.layout.fragment_eksi, container, false);
    }
public void set(String prop,String val){
	loc=getActivity().getSharedPreferences("eksiofflinethis",0);
	loc.edit().putString(prop,val).commit();
}
public String get(String prop){
	loc=getActivity().getSharedPreferences("eksiofflinethis",0);
	return loc.getString(prop,"");
}
	public static String toString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        lst=(ListView) view.findViewById(R.id.listView);
		foot=new ProgressBar(getActivity());
		lst.addFooterView(foot);
		lst.setOnScrollListener(new OnScrollListener(){

				@Override
				public void onScrollStateChanged(AbsListView p1, int p2)
				{
					// TODO: Implement this method
				}

				@Override
				public void onScroll(AbsListView p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					
					if(lastSeen!=p4 && p2+p3==p4){
						
						foot.setVisibility(View.VISIBLE);
						new LoadXML().execute(linkk);
						lastSeen=p4;
					}
				}
			});
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("EkşiOffline");
		alert.setMessage("başlık veya kişi girin");
// Set an EditText view to get user input 
		final EditText input = new EditText(getActivity());
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				//	String value = input.getText();
					// Do something with value!
					try
					{
						URI sa=null;
						try
						{
							sa= new URI("http", "www.cuyazilim.com", "/cantest/eksi.php", "q=" + (input.getText() + ""),null);
						}
						catch (URISyntaxException e)
						{}
						entryList.clear();
						authorList.clear();
						favList.clear();
						dateList.clear();
						EksiActivity.setSub(input.getText().toString());
			//		Toast.makeText(getActivity(),"http://www.cuyazilim.com/cantest/eksi.php?q=" + URLEncoder.encode((sa.toASCIIString().substring(44)).replaceAll(" ","+"), "UTF-8"),Toast.LENGTH_LONG).show();
			//			new LoadXML().execute(sa.toASCIIString().replaceAll("%20","+"));
						new LoadXML().execute("http://www.cuyazilim.com/cantest/eksi.php?q=" + URLEncoder.encode((sa.toASCIIString().substring(44)).replaceAll(" ","+"), "UTF-8"));
					}
					catch (UnsupportedEncodingException e)
					{}
				}
			});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});
		alert.show();
        TextView ass=(TextView) view.findViewById(R.id.textView3);
		ass.setClickable(true);
		//ass.setLinksClickable(true);
		ass.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					new LoadXML().execute(linkk);
				}
			});
    }
    public class LoadXML extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPostExecute(Void aVoid) {
//if(entryList.size()>0){
	if (adap==null){
		adap=new MyCustomAdapter(getActivity(),R.layout.activity_eksi,entryList);
		lst.setAdapter(adap);
	}else{
		adap.notifyDataSetChanged();
	}
            
		//	}
		//	Toast.makeText(getActivity(),linkk,Toast.LENGTH_LONG).show();
         //   dialog.dismiss();
			//lst.removeFooterView(foot);
			foot.setVisibility(View.GONE);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  dialog.setMessage("Yükleniyor");
          //  dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            //  d = params[0];
            getDataFromXml(params[0]);
            return null;
        }
    }
	
    public ArrayList<String> getDataFromXml(String s) {

        ArrayList<String> list = new ArrayList<String>();
        
        try {

            URL url = new URL(s);
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

            Document document;
			String readString=get(s);
			if(!readString.equals("")){
				document=dBuilder.parse(new InputSource(new StringReader(readString)));
			}else{
				document = dBuilder.parse(new InputSource(url.openStream()));
				set(s,toString(document));
			}
			
            document.getDocumentElement().normalize();
			linkk=document.getElementsByTagName("link").item(0).getChildNodes().item(0).getNodeValue();
            NodeList nodeListCountry = document.getElementsByTagName("item");
			if(nodeListCountry.getLength()>0){
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
				nodeListText = elementMain.getElementsByTagName("date");
				elementText = (Element) nodeListText.item(0);


                if (elementText.getChildNodes().getLength() > 0) {
                    dateList.add(elementText.getChildNodes().item(0).getNodeValue());
                } else {
                    dateList.add("0");
                }
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
		} catch (DOMException e) {
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
			public TextView date;
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
				holder.date = (TextView) row.findViewById(R.id.adapterTextView1);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            Spanned spanned= Html.fromHtml(entryList.get(position));
            holder.entry.setText(spanned, TextView.BufferType.SPANNABLE);
			holder.entry.setMovementMethod(LinkMovementMethod.getInstance());
            holder.author.setText(authorList.get(position));
			holder.date.setText(dateList.get(position));
			((TextView) row.findViewById(R.id.textnumb)).setText(position+1+"");
            //	  ((ImageView) row.findViewById(R.id.ImgLang)).setImageResource(0);

            //    imageLoader.displayImage(xmlSImg.get(position), holder.image, options, animateFirstListener);
            return row;
        }

    }

}
