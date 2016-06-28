package br.org.iupi.condominio.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.adapter.NavDrawerListAdapter;
import br.org.iupi.condominio.helper.NFCHelper;
import br.org.iupi.condominio.model.AcaoTagNFC;
import br.org.iupi.condominio.model.NavDrawerItem;

@SuppressWarnings("deprecation")
public class Principal extends Activity {

	private NFCHelper nfcHelper;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIcons;

	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mNavDrawerListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);

		nfcHelper = new NFCHelper(this);

		if (nfcHelper.isNFCEnabledDevice()) {
			Toast.makeText(this, R.string.msg_nfc_disponivel, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, R.string.msg_nfc_nao_disponivel, Toast.LENGTH_LONG).show();
		}

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		mNavMenuTitles = getResources().getStringArray(R.array.lst_txt_nfc_acoes_tag);

		// nav drawer icons from resources
		mNavMenuIcons = getResources().obtainTypedArray(R.array.lst_img_nfc_acoes_tag);

		mNavDrawerItems = criaListaNavDrawerItem(mNavMenuTitles, mNavMenuIcons);

		// Recycle the typed array
		mNavMenuIcons.recycle();

		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		mNavDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(), mNavDrawerItems);
		mDrawerList.setAdapter(mNavDrawerListAdapter);

		configuraActionBar();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = criaActionBarDrawerToggle(mDrawerLayout);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private ArrayList<NavDrawerItem> criaListaNavDrawerItem(String[] navMenuTitles, TypedArray navMenuIcons) {
		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "30+"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, "18"));

		return navDrawerItems;
	}

	private void configuraActionBar() {
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));
	}

	private ActionBarDrawerToggle criaActionBarDrawerToggle(DrawerLayout drawerLayout) {
		ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_menu_16, // nav
																													// menu
																													// toggle
																													// icon
				R.string.txt_nome_aplicacao, // nav drawer open - description
												// for accessibility
				R.string.txt_nome_aplicacao // nav drawer close - description
											// for accessibility
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

		return drawerToggle;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		try {
			if (nfcHelper.isNFCIntent(intent)) {
				NdefMessage ndefMessage = nfcHelper.getNdefMessageFromIntent(intent);

				if (isValidTag(ndefMessage)) {
					int idAcaoTag = getIdAcaoTag(ndefMessage);

					displayView(idAcaoTag);
				} else {
					// não faz nada e informa ao usuário que a tag é inválida
					Toast.makeText(this, R.string.msg_nfc_tag_invalida, Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			Log.e("onNewIntent", e.getMessage());
		}

		super.onNewIntent(intent);
	}

	private boolean isValidTag(NdefMessage ndefMessage) {
		NdefRecord[] ndefRecords = nfcHelper.getNdefRecords(ndefMessage);

		if (ndefRecords != null) {
			if (ndefRecords.length != 3) {
				return false;
			}

			// isValid = (ndefRecords[0].toString() ==
			// this.activity.getPackageName()) ? true : false;

			if (!isValidAcaoTag(ndefRecords[1])) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean isValidAcaoTag(NdefRecord ndefRecord) {
		boolean isTextRecord = nfcHelper.isNdefRecordOfTnAndRdt(ndefRecord, NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_TEXT);

		if (isTextRecord) {
			String textInRecord = nfcHelper.getTextFromNdefRecord(ndefRecord);

			if (AcaoTagNFC.isValid(textInRecord)) {
				return true;
			}
		}

		return false;
	}

	private int getIdAcaoTag(NdefMessage ndefMessage) {
		NdefRecord[] ndefRecords = nfcHelper.getNdefRecords(ndefMessage);

		if (ndefRecords != null) {
			String textInRecord = nfcHelper.getTextFromNdefRecord(ndefRecords[1]);

			return AcaoTagNFC.get(textInRecord).ordinal();
		}

		return -1;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (nfcHelper.isNFCEnabledDevice()) {
			nfcHelper.enableForegroundDispatch();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (nfcHelper.isNFCEnabledDevice()) {
			nfcHelper.disableForegroundDispatch();
		}
	}

	/**
	 * Slide menu item click listener
	 */
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		Intent intent = null;

		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.mnuGravaTagNFC:
			intent = new Intent(this, GravaTagNFC.class);
			startActivity(intent);
			return true;

		case R.id.mnuAgendaAlarme:
			intent = new Intent(this, AgendaAlarme.class);
			startActivity(intent);
			return true;

		/*
		 * case R.id.mnuAlertaRonda: intent = new Intent(this,
		 * AlertaRonda.class); startActivity(intent); return true;
		 */

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.mnuGravaTagNFC).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new LeituraConsumoFragment();
			break;
		case 1:
			fragment = new RondasFragment();
			break;
		case 2:
			fragment = new FuncionalidadeIndisponivelFragment();
			break;
		case 3:
			fragment = new FuncionalidadeIndisponivelFragment();
			break;
		case 4:
			fragment = new FuncionalidadeIndisponivelFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(mNavMenuTitles[position]);
			// getActionBar().setLogo(navMenuIcons.getResourceId(position, -1));
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
}