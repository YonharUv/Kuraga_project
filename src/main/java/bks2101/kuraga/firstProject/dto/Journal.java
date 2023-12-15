package bks2101.kuraga.firstProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal implements Serializable {
    private Map<String, Map<String, MeetingList>> journal = new HashMap<>();
    public void addList(String curatorName, String groupName, MeetingList list) {
        Map<String, MeetingList> journalList = new HashMap<>();
        journalList.put(groupName, list);
        this.journal.put(curatorName, journalList);
    }
    public boolean check(String curatorName, String groupName) {
        if (!this.journal.containsKey(curatorName)) {
            return false;
        }
        var list = journal.get(curatorName);
        return list.containsKey(groupName);
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Journal loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Journal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}


