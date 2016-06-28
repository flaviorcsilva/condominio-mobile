package br.org.iupi.condominio.helper;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

public class NFCHelper {

	private Activity activity;
	private NfcAdapter nfcAdapter;

	public NFCHelper(Activity activity) {
		this.activity = activity;
		this.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
	}

	public boolean isNFCEnabledDevice() {
		boolean hasFeature = activity.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_NFC);
		boolean isEnabled = (nfcAdapter != null && nfcAdapter.isEnabled());

		return hasFeature && isEnabled;
	}

	public void enableForegroundDispatch() {
		Intent intent = new Intent(activity, activity.getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0,
				intent, 0);

		IntentFilter[] intentFilter = new IntentFilter[] {};

		String[][] techList = new String[][] {
				{ android.nfc.tech.Ndef.class.getName() },
				{ android.nfc.tech.NdefFormatable.class.getName() } };

		if (Build.DEVICE.matches(".*generic.*")) {
			// clean up the tech filter when in emulator since it doesn't work
			// properly.
			techList = null;
		}

		nfcAdapter.enableForegroundDispatch(activity, pendingIntent,
				intentFilter, techList);
	}

	public void disableForegroundDispatch() {
		nfcAdapter.disableForegroundDispatch(activity);
	}

	public NdefMessage getNdefMessageFromIntent(Intent intent) {
		NdefMessage ndefMessage = null;

		Parcelable[] extra = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

		if (extra != null && extra.length > 0) {
			ndefMessage = (NdefMessage) extra[0];
		}

		return ndefMessage;
	}

	public NdefRecord[] getNdefRecords(NdefMessage ndefMessage) {
		NdefRecord[] ndefRecords = ndefMessage.getRecords();

		if (ndefRecords != null && ndefRecords.length > 0) {
			return ndefRecords;
		} else {
			return null;
		}
	}

	public boolean isNdefRecordOfTnAndRdt(NdefRecord ndefRecord, short tnf,
			byte[] rdt) {
		return ndefRecord.getTnf() == tnf
				&& Arrays.equals(ndefRecord.getType(), rdt);
	}

	public String getTextFromNdefRecord(NdefRecord ndefRecord) {
		String tagContent = null;

		try {
			byte[] payload = ndefRecord.getPayload();

			String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8"
					: "UTF-16";

			int languageSize = payload[0] & 0063;

			tagContent = new String(payload, languageSize + 1, payload.length
					- languageSize - 1, textEncoding);
		} catch (UnsupportedEncodingException uee) {
			Log.e("getTextFromNdefRecord", uee.getMessage(), uee);
		}

		return tagContent;
	}

	public boolean formatTag(Tag tag, NdefMessage ndefMessage) {
		try {
			NdefFormatable ndefFormat = NdefFormatable.get(tag);

			if (ndefFormat != null) {
				ndefFormat.connect();
				ndefFormat.format(ndefMessage);
				ndefFormat.close();

				return true;
			}
		} catch (Exception e) {
			Log.e("formatTag", e.getMessage());
		}

		return false;
	}

	public boolean writeNdefMessage(Tag tag, NdefMessage ndefMessage) {
		try {
			if (tag != null) {
				Ndef ndef = Ndef.get(tag);

				if (ndef == null) {
					return formatTag(tag, ndefMessage);
				} else {
					ndef.connect();

					if (ndef.isWritable()) {
						ndef.writeNdefMessage(ndefMessage);
						ndef.close();

						return true;
					}

					ndef.close();
				}
			}
		} catch (Exception e) {
			Log.e("formatTag", e.getMessage());
		}

		return false;
	}

	public boolean isNFCIntent(Intent intent) {
		return intent.hasExtra(NfcAdapter.EXTRA_TAG);
	}

	public NdefRecord createTextRecord(String content) {
		try {
			byte[] language;
			language = Locale.getDefault().getLanguage().getBytes("UTF-8");
			final int languageSize = language.length;

			final byte[] text = content.getBytes("UTF-8");
			final int textLength = text.length;

			final ByteArrayOutputStream payload = new ByteArrayOutputStream(1
					+ languageSize + textLength);
			payload.write((byte) (languageSize & 0x1F));
			payload.write(language, 0, languageSize);
			payload.write(text, 0, textLength);

			return new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
					NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
		} catch (UnsupportedEncodingException uee) {
			Log.e("createTextRecord", uee.getMessage());
		}

		return null;
	}
}
