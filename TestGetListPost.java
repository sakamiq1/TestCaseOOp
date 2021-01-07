import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import com.google.gson.Gson;

public class TestGetListPost {


	public static void main(String[] args)
			throws MalformedURLException, ProtocolException, IOException {
		URL url = new URL(Constant.Get_List_Post_Case3
				);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		
		try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {

			StringBuilder content;

			if(connection.getResponseCode() == 200) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String line;
					content = new StringBuilder();
					while ((line = in.readLine()) != null) {
						content.append(line);
						content.append(System.lineSeparator());
					}
				}
			}else {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String line;
					content = new StringBuilder();
					while ((line = in.readLine()) != null) {
						content.append(line);
						content.append(System.lineSeparator());
					}
				}
			}
			System.out.println(content.toString());
			
			Gson g = new Gson();
			Response rp = g.fromJson(content.toString(), Response.class);

			assert (rp.code != null && !"".equals(rp.code));
			assert (rp.message != null && !"".equals(rp.message));
//			
		} finally {
			connection.disconnect();
		}
	}

	

	class Response {
		public String code;
		public String message;
	}
}
