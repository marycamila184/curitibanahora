package br.com.curitibanahora;

import br.com.curitibanahora.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailCulturaFragment extends Fragment {

	private View parentView;

	// private AdView adView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment_detail, container,
				false);
		getActivity().getActionBar().hide();
		// load content
		WebView webview = (WebView) parentView.findViewById(R.id.webView1);
		webview.loadDataWithBaseURL("",
				CulturaFragment.currentElement.getDESCRIPTION()
						+ "</br></br></br></br>", "text/html", "UTF-8", "");

		TextView titre = (TextView) parentView.findViewById(R.id.textView1);
		TextView date = (TextView) parentView.findViewById(R.id.textView2);

		titre.setText(CulturaFragment.currentElement.getTITLE());
		date.setText(CulturaFragment.currentElement.getPUBDATE());

		// ADMOB
		// adView = new AdView(getActivity());
		// adView.setAdUnitId(Config.YOUR_ADMOB_ID);
		// adView.setAdSize(AdSize.BANNER);
		// LinearLayout layout = (LinearLayout) parentView
		// .findViewById(R.id.mainLayout);
		// layout.addView(adView);
		// AdRequest adRequest = new AdRequest.Builder().build();
		// adView.loadAd(adRequest);

		return parentView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().getActionBar().show();
	}

}
