package web.client.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.client.Category;
import web.client.Repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository repository;

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    public void saveCategory(Category category) {
        repository.save(category);
    }

    public List<Category> getAllCategories() {
        return repository.findAllByOrderByName();
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public boolean existsWithId(long id) {
        return repository.existsById(id);
    }

    public Category getById(long id) {
        return repository.getOne(id);
    }

}
