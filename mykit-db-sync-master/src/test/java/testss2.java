import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class testss2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
        String urlWithCe = "jdbc:mysql://172.29.1.231:3306/unicorndb_wz?" +
                "useSSL=true&trustCertificateKeyStorePassword=123456&" +
                "trustCertificateKeyStoreUrl=http://172.29.1.18:19999/mysql231.jks&" +
                "allowMultiQueries=true&" +
                "useUnicode&characterEncoding=UTF-8&" +
                "verifyServerCertificate=false&requireSSL=true";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(urlWithCe,
                    "509_test", "1234Qwer+");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from " +
                    "tb_gap_data");
            System.out.println(preparedStatement.executeQuery().first());
        } catch (Exception exception) {
            exception.printStackTrace();
        }


	}

}
