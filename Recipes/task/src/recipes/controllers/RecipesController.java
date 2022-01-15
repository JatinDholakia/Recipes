package recipes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import recipes.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.repo.RecipeRepository;
import recipes.repo.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipesController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/recipe/new")
    public Map<String, Long> addRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return Map.of("id", savedRecipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipe.get();
    }


    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id,
                                               Authentication authentication) {
        if (!recipeRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else if (!recipeRepository.findById(id).get().getCreatedBy().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        recipeRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable Long id,
                                               @RequestBody @Valid Recipe recipe,
                                               Authentication authentication) {
        Optional<Recipe> savedRecipe = recipeRepository.findById(id);
        if(savedRecipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else if (!savedRecipe.get().getCreatedBy().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setId(id);
        recipe.setCreatedBy(savedRecipe.get().getCreatedBy());
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipe(@RequestParam(required = false) Optional<String> category,
                                     @RequestParam(required = false) Optional<String> name) {
        if(category.isPresent() == name.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (category.isPresent()) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category.get());
        } else {
            return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name.get());
        }
    }

    @DeleteMapping("/api/recipe/all")
    public ResponseEntity<Object> deleteAll() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
