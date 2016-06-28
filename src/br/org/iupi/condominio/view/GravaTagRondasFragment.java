package br.org.iupi.condominio.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.org.iupi.condominio.R;

public class GravaTagRondasFragment extends Fragment {

	public GravaTagRondasFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.grava_tag_rondas_fragment,
				container, false);

		return rootView;
	}
}