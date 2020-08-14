package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectsoft.cookingapp.data.model.entity.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findAllByUser_IdContains(String id);
}
