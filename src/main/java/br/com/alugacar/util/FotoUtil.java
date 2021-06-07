package br.com.alugacar.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class FotoUtil {

	static final String PATH_FOTOS = System.getProperty("user.home") + "/alugacar/imagens/";

	public static File getFoto(String subpasta, String nomeFoto, ExtensaoFoto extensao) {
		String pastaArquivo = PATH_FOTOS + subpasta + "/";
		File file = new File(pastaArquivo + nomeFoto + "." + extensao.name());

		return file.exists() ? file : null;
	}

	public static void salvarFoto(InputStream inpsFoto, String subpasta, String nomeFoto, ExtensaoFoto extensao)
			throws FileNotFoundException, IOException {
		File fotoSalva = new File(PATH_FOTOS + subpasta + "/" + nomeFoto + "." + extensao.name());
		IOUtils.copyLarge(inpsFoto, new FileOutputStream(fotoSalva));
	}

	public static boolean validarFotoUpload(UploadedFile foto) {
		String nomeFoto = foto.getFileName().toUpperCase();
		boolean isValida = false;
		for (ExtensaoFoto extensao : ExtensaoFoto.values()) {
			if (nomeFoto.contains("." + extensao.name())) {
				isValida = true;
				break;
			}
		}
		return isValida;
	}

	public enum ExtensaoFoto {
		JPG, JPEG, PNG;
	}
}
