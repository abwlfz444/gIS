import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private List<Member> members;
    private static final String CSV_FILE = "members.csv";

    public MemberManager() {
        members = new ArrayList<>();
        loadMembersFromCSV();
    }

    public void addMember(Member member) {
        members.add(member);
        saveMembersToCSV();
    }

    public void removeMember(Member member) {
        members.remove(member);
        saveMembersToCSV();
    }

    public List<Member> getMembers() {
        return members;
    }

    private void loadMembersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    members.add(new Member(values[0], values[1], values[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMembersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Member member : members) {
                bw.write(member.getFirstName() + "," + member.getLastName() + "," + member.getMemberId());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 