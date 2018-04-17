import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class sample
{
	public static void main(String[] args) throws Exception
	{
		String url = "https://localhost:9980/lool/convert-to/pdf";
		File file = new File("/path/to/test.txt");
		File outFile = new File("/path/to/test.pdf");
		String boundary = Long.toHexString(System.currentTimeMillis());
		String CRLF = "\r\n";

		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		OutputStream output = connection.getOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output), true);

		writer.append("--" + boundary).append(CRLF);
		writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + file.getName() + "\"").append(CRLF);
		writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
		writer.append("Content-Transfer-Encoding: binary").append(CRLF);
		writer.append(CRLF).flush();
		Files.copy(file.toPath(), output);
		output.flush();
		writer.append(CRLF).flush();
		writer.append("--" + boundary + "--").append(CRLF).flush();

		HttpURLConnection httpConnection = (HttpURLConnection)connection;
		int responseCode = httpConnection.getResponseCode();
		InputStream response = httpConnection.getInputStream();
		Files.copy(response,  outFile.toPath());
	}
}
