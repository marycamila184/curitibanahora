package br.com.curitibanahora;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import br.com.curitibanahora.adapters.ArticleAdapter;
import br.com.curitibanahora.object.Article;
import br.com.curitibanahora.parser.SaxGestionnaire;

public class TempoFragment extends Fragment {

	private View parentView;
	public static ArrayList<Article> listArticle;
	public static ArrayList<Article> currrentListArticle;
	public static ArrayList<String> listCategorie;
	private ListView listView;
	private ProgressBar progress;
	public static Article currentElement;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		// Handle action bar actions click
		if (item.getItemId() == R.id.refresh) {
			// Toast.makeText(this, "hsuahu", Toast.LENGTH_SHORT).show();
			listArticle = new ArrayList<Article>();
			LoadArticle task = new LoadArticle();
			task.execute();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.fragment_tempo, container, false);

		listView = (ListView) parentView.findViewById(R.id.listView1);
		progress = (ProgressBar) parentView.findViewById(R.id.progressBar1);

		// // refresh list
		// MainActivity.refresh.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if(temConexao(getActivity())){
		// listArticle = new ArrayList<Article>();
		// LoadArticle task = new LoadArticle();
		// task.execute();
		// }else{
		// mostraAlerta();
		// }
		// }
		// });

		// load lists
		LoadArticle task = new LoadArticle();
		task.execute();

		return parentView;
	}

	// load article asynctask
	private class LoadArticle extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			listArticle = SaxGestionnaire.getArticles();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.setVisibility(View.INVISIBLE);
			if (getActivity() != null && listArticle != null) {
				// load category

				currrentListArticle = new ArrayList<Article>();

				for (int i = 0; i < listArticle.size(); i++) {
					if (listArticle.get(i).getCATEGORY().compareTo("Tempo ") == 0) {
						currrentListArticle.add(listArticle.get(i));
					}
				}

				ArticleAdapter adapter = new ArticleAdapter(getActivity(),
						currrentListArticle);
				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// get the current element
						currentElement = currrentListArticle.get(position);

						// Create new fragment and transaction
						Fragment newFragment = new DetailTempoFragment();
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();
						// transaction.setCustomAnimations(R.anim.righttoleft,
						// R.anim.righttoleft);

						// Replace whatever is in the
						// fragment_container view with this
						// fragment,
						// and add the transaction to the back
						// stack
						transaction.add(R.id.frame_container, newFragment);
						transaction.hide(TempoFragment.this);
						transaction.addToBackStack(TempoFragment.class
								.getName());
						transaction.commit();
					}
				});

				// }
				// });
				// }

				// currrentListArticle = listArticle;

				MainActivity.listArticle = listArticle;

				ArticleAdapter adapterD = new ArticleAdapter(getActivity(),
						currrentListArticle);

				listView.setAdapter(adapterD);

				// click on a item
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						// get the current element
						currentElement = currrentListArticle.get(position);
						// Create new fragment and transaction
						Fragment newFragment = new DetailTempoFragment();
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();
						// transaction.setCustomAnimations(R.anim.righttoleft,
						// R.anim.righttoleft);

						// Replace whatever is in the fragment_container view
						// with this fragment,
						// and add the transaction to the back stack
						transaction.add(R.id.frame_container, newFragment);
						transaction.hide(TempoFragment.this);
						transaction.addToBackStack(TempoFragment.class
								.getName());
						// Commit the transaction
						transaction.commit();
					}
				});
			}
		}
	}

	private boolean temConexao(Context classe) {
		// Pego a conectividade do contexto passado como argumento
		ConnectivityManager gerenciador = (ConnectivityManager) classe
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo informacao = gerenciador.getActiveNetworkInfo();
		// Se o objeto for nulo ou nao tem conectividade retorna false
		if ((informacao != null) && (informacao.isConnectedOrConnecting())
				&& (informacao.isAvailable())) {
			return true;
		}
		return false;
	}

	private void mostraAlerta() {
		AlertDialog.Builder informa = new AlertDialog.Builder(getActivity());
		informa.setTitle("Acesso a Internet")
				.setMessage(
						"Sem conexão com a internet. Favor verificar a conexão e tentar novamente!");
		informa.setNeutralButton("Voltar", null).show();
		// onDestroy();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		((MainActivity) getActivity()).onFragmentViewCreated(view,
				"Previsão do Tempo"); // Metodo
	}

}
