package br.com.curitibanahora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.curitibanahora.ApplicationClass.TrackerName;
import br.com.curitibanahora.adapters.NavDrawerListAdapter;
import br.com.curitibanahora.config.Config;
import br.com.curitibanahora.model.NavDrawerItem;
import br.com.curitibanahora.object.Article;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseInstallation;

public class MainActivity extends Activity {

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	public static ArrayList<Article> listArticle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	//private LinkedList<String> channels = null;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
	//	List<String> navMenuTitles=new ArrayList<String>();
	//	navMenuTitles = ParseInstallation.getCurrentInstallation().getList("channels");
		
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
//		if(navMenuTitles==null){
//			ParsePush push = new ParsePush();
//			push.setChannel("⁄ltimas ");
//			navMenuTitles = ParseInstallation.getCurrentInstallation().getList("channels");
//		}

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		for (int i = 0; i < navMenuTitles.length; i++) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], -1));
		}

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		if (temConexao(MainActivity.this) == false) {
			mostraAlerta();
		}

		// push config
		// String android_id =
		// Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
		// Parse.initialize(this, "JHB2W0B9y1guh9h4JXZASVNiVfccz81kukiexs5I",
		// "lffGSQueeWmkkPunTL04MaNKIx7qA5ITvnuSm6DO");
		// PushService.setDefaultPushCallback(MainActivity.this,
		// MainActivity.class);
		// ParseInstallation installation =
		// ParseInstallation.getCurrentInstallation();
		// installation.put("UniqueId",android_id);
		//
		// installation.saveInBackground();
		// ParseAnalytics.trackAppOpened(getIntent());
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// Log.d(TAG, "MainActivitiy.onOptionsItemSelected");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else if (item.getItemId() == R.id.refresh) {
			return false;
		}

		// Handle action bar actions click

		return super.onOptionsItemSelected(item);

	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new UltimasFragment();
			break;
		case 1:
			fragment = new CidadeFragment();
			break;
		case 2:
			fragment = new CulturaFragment();
			break;
		case 3:
			fragment = new TempoFragment();
			break;
		case 4:
			fragment = new TransitoFragment();
			break;
		case 5:
			openAlertCategories();
			break;
		case 6:
			openAlertAbout();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void onFragmentViewCreated(View view, String cat) {
		// Iniciar os campos buscando no layout do Fragment
		TextView categoria = (TextView) view.findViewById(R.id.textCategoria);
		categoria.setText(cat);
	}

	public void callFrag() {
		startActivity(new Intent(getApplicationContext(), UltimasFragment.class));
	}

	public void openAlertAbout() {
		// LayoutInflater È utilizado para inflar nosso layout em uma view.
		// //-pegamos nossa instancia da classe
		LayoutInflater li = getLayoutInflater();

		View view = li.inflate(R.layout.sobre, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Sobre");
		builder.setView(view);
		final AlertDialog alerta = builder.create();

		alerta.show();
		ImageView fb = (ImageView) view.findViewById(R.id.imageView5);
		ImageView twitter = (ImageView) view.findViewById(R.id.imageView4);
		ImageView google = (ImageView) view.findViewById(R.id.imageView6);

		// facebook action
		fb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Config.URLFacebook));
				startActivity(i);
			}
		});

		// twitter action
		twitter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Config.URLTwitter));
				startActivity(i);
			}
		});

		// google action
		google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Config.URLGoogle));
				startActivity(i);
			}
		});
	}

	// private void hideSystemUI() {
	// // Set the IMMERSIVE flag.
	// // Set the content to appear under the system bars so that the content
	// // doesn't resize when the system bars hide and show.
	// mDecorView.setSystemUiVisibility(
	// View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	// | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	// | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	// | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	// | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	// | View.SYSTEM_UI_FLAG_IMMERSIVE);
	// }

	// Mostra a informa√ß√£o caso n√£o tenha internet.
	private void mostraAlerta() {
		AlertDialog.Builder informa = new AlertDialog.Builder(MainActivity.this);
		informa.setTitle("Acesso a Internet")
				.setMessage(
						"Sem conex„o com a internet. Favor verificar a conex„o e tentar novamente!");
		informa.setNeutralButton("Voltar", null).show();
		// onDestroy();
	}

	private boolean temConexao(Context classe) {
		// Pego a conectividade do contexto passado como argumento
		ConnectivityManager gerenciador = (ConnectivityManager) classe
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// Crio a vari√°vel informacao que recebe as informa√ß√µes da Rede
		NetworkInfo informacao = gerenciador.getActiveNetworkInfo();
		// Se o objeto for nulo ou nao tem conectividade retorna false
		if ((informacao != null) && (informacao.isConnectedOrConnecting())
				&& (informacao.isAvailable())) {
			return true;
		}
		return false;
	}

	@Override
	protected void onStart() {
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
		super.onStop();
	}

	public void openAlertCategories() {
		// LayoutInflater È utilizado para inflar nosso layout em uma view.
		// pegamos nossa instancia da classe
		LayoutInflater li = getLayoutInflater();

		View view = li.inflate(R.layout.categorias, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Categorias");
		builder.setView(view);
		
		final AlertDialog alerta = builder.create();
		alerta.show();
		final List<String> cleanchannels = new ArrayList<String>();
		cleanchannels.add("");
		final List<String> channels = new ArrayList<String>();
		List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
		final CheckBox checkbox1=(CheckBox)view.findViewById(R.id.checkBox1);
		final CheckBox checkbox2=(CheckBox)view.findViewById(R.id.checkBox2);
		final CheckBox checkbox3=(CheckBox)view.findViewById(R.id.checkBox3);
		final CheckBox checkbox4=(CheckBox)view.findViewById(R.id.checkBox4);
		final CheckBox checkbox5=(CheckBox)view.findViewById(R.id.checkBox5);
		if(!subscribedChannels.isEmpty()){
			//Vejo os canais que o cliente possui
 			for (String string : subscribedChannels) {
				if(string.equals("Cidade")){
					checkbox2.setChecked(true);
					channels.add("Cidade");
				}else if(string.equals("Cultura")){
					channels.add("Cultura");
					checkbox3.setChecked(true);					
				}else if(string.equals("Tempo")){
					channels.add("Tempo");
					checkbox5.setChecked(true);
				}else if(string.equals("Tr‚nsito")){
					channels.add("Tr‚nsito");
					checkbox4.setChecked(true);
				}else if(string.equals("Todas")){
					channels.add("Ultimas");
					checkbox1.setChecked(true);
				}
			}
		}
		//Limpo os canais
		ParseInstallation install = ParseInstallation.getCurrentInstallation();
		install.put("channels", cleanchannels);
		
		
		install.saveEventually();
		
		Button botao=(Button)view.findViewById(R.id.botao);
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Seto os novos canais
				ParseInstallation install = ParseInstallation.getCurrentInstallation();
				if(checkbox2.isChecked()){
					channels.add("Cidade");
				}
				if(checkbox3.isChecked()){
					channels.add("Cultura");
				}
				if(checkbox5.isChecked()){
					channels.add("Tempo");
				}
				if(checkbox4.isChecked()){
					channels.add("Transito");
				}
				if(checkbox1.isChecked()){
					channels.add("Ultimas");
				}
				install.put("channels", channels);
				
		
				install.saveEventually();
				//DatabaseHandler db=new DatabaseHandler(getApplicationContext());
				//channels=new LinkedList<String>();
				
			}
		});
		//alerta.cancel();
	}
}
