package br.org.iupi.condominio.view;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.model.Leitura;
import br.org.iupi.condominio.model.TipoLeitura;

public class LeituraFormularioHelper {

	private Leitura leitura;

	private ImageView imgFoto;
	private EditText edtUnidade;
	private Spinner spnTipoLeitura;
	private EditText edtMedido;
	private byte[] foto;

	public LeituraFormularioHelper(LeituraFormularioActivity leituraFormularioActivity) {
		imgFoto = (ImageView) leituraFormularioActivity.findViewById(R.id.leitura_formulario_imgFoto);
		edtUnidade = (EditText) leituraFormularioActivity.findViewById(R.id.leitura_formulario_edtUnidade);
		spnTipoLeitura = (Spinner) leituraFormularioActivity.findViewById(R.id.leitura_formulario_spnTipoLeitura);
		edtMedido = (EditText) leituraFormularioActivity.findViewById(R.id.leitura_formulario_edtMedido);

		leitura = new Leitura();
	}

	public Leitura getLeitura() {
		leitura.setUnidade(edtUnidade.getText().toString());
		leitura.setTipo(TipoLeitura.get(spnTipoLeitura.getSelectedItem().toString()));
		leitura.setMedido(Integer.valueOf(edtMedido.getText().toString()));
		leitura.setFoto(foto);

		return leitura;
	}

	public void populaFormulario(Leitura leitura) {
		edtUnidade.setText(leitura.getUnidade());
		spnTipoLeitura.setSelection(leitura.getTipo().ordinal());
		edtMedido.setText(leitura.geMedido().toString());

		if (leitura.getFoto() != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(leitura.getFoto(), 0, leitura.getFoto().length);
			imgFoto.setImageBitmap(bitmap);
			imgFoto.setScaleType(ImageView.ScaleType.FIT_XY);
			foto = leitura.getFoto();
		}

		this.leitura = leitura;
	}

	public void carregaFoto(String diretorioFoto) {
		if (diretorioFoto != null) {
			BitmapFactory.Options options = new BitmapFactory.Options();

			// Downsizing da foto, para não incorrer em OutOfMemory para imagens
			// grandes
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(diretorioFoto, options);

			imgFoto.setImageBitmap(bitmap);
			imgFoto.setScaleType(ImageView.ScaleType.FIT_XY);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			this.foto = baos.toByteArray();
		}

		/*
		 * Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto); Bitmap
		 * bitmapReduzido = bitmap.createScaledBitmap(bitmap, 300, 300, true);
		 */
	}
}