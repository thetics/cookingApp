package projectsoft.cookingapp.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import projectsoft.cookingapp.data.model.view.NoteViewModel;
import projectsoft.cookingapp.data.service.models.NoteServiceModel;
import projectsoft.cookingapp.data.service.services.NoteService;
import projectsoft.cookingapp.data.service.services.UserService;
import projectsoft.cookingapp.web.controllers.base.BaseController;
import projectsoft.cookingapp.web.models.binding.NoteCreateBindingModel;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/notes")
public class NoteController extends BaseController {
    private final NoteService noteService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public NoteController(NoteService noteService, UserService userService, ModelMapper modelMapper) {
        super(userService);
        this.noteService = noteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView createNote(ModelAndView modelAndView, Principal principal) {
        var user = getUsername(principal);

        modelAndView.addObject("username", user.getUsername());
        modelAndView.setViewName("notes/note-create");

        return modelAndView;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView createNoteConfirm(Principal principal, @ModelAttribute NoteCreateBindingModel model) {
        var user = getUsername(principal);
        NoteServiceModel note = this.modelMapper.map(model, NoteServiceModel.class);

        System.out.println();
        note.setUser(user);
        note.setCreateTime(new Date());
        this.noteService.createNote(note);

        return super.redirect("/notes/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView getAllUsersNotes(ModelAndView modelAndView, Principal principal, @ModelAttribute NoteViewModel model) {
        var user = getUsername(principal);

        var notes = this.noteService.getAllUserNotes(user.getId());

        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("notes", notes);
        modelAndView.setViewName("fragments/saved-notes");

        return super.view("notes/all", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView getNote(@PathVariable String id, Principal principal, ModelAndView modelAndView) {
        var user = getUsername(principal);

        var note = this.noteService.findById(id);


        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("note", note);
        modelAndView.addObject("noteId", id);

        modelAndView.setViewName("notes/details-note");
        return modelAndView;

    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView deleteNoteConfirm(@PathVariable String id) {
        this.noteService.deleteNote(id);

        return super.redirect("/notes/all");
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView deleteNoteConfirmpost(@PathVariable String id) {
        this.noteService.deleteNote(id);

        return super.redirect("/notes/all");
    }
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView editNote(Principal principal, @PathVariable String id, ModelAndView modelAndView) {
        var note = this.noteService.findById(id);
        var user = getUsername(principal);

        modelAndView.addObject("note", note);
        modelAndView.addObject("noteId", id);
        modelAndView.addObject("username", user.getUsername());

        modelAndView.setViewName("notes/note-edit");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView editGroupConfig(@PathVariable String id, @ModelAttribute NoteCreateBindingModel model) {
        NoteServiceModel noteServiceModel = this.modelMapper.map(model, NoteServiceModel.class);

        this.noteService.editNote(id, noteServiceModel);

        return super.redirect("/notes/details/" + id);
    }
}
