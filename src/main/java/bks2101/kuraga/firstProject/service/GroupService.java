package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.models.Group;
import bks2101.kuraga.firstProject.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    Group getGroupByName(String groupName) {
        if (!groupRepository.existsByName(groupName)) {
            return null;
        }
        return groupRepository.findByName(groupName);
    }
}
