package sample.DAL.database;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.BE.Message;
import sample.DAL.IMessageTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 11:36 AM
 */
public class MessageTableDAO implements IMessageTable {

    private DBconnector dBconnector = new DBconnector();

    @Override
    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        String query = "SELECT Text FROM Messages;";
        try (Connection con = dBconnector.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                String text = rs.getString("Text");
                messageList.add(new Message(text));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return messageList;
    }

    @Override
    public void saveMessage(Message message) {
        String query = "INSERT INTO Messages(Text) Values(?);";
        try (Connection con = dBconnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
        ) {
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.executeUpdate();


        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
