package matt.project.spring5restmvc.api.v1.mapper;

import matt.project.spring5restmvc.api.v1.model.CategoryDTO;
import matt.project.spring5restmvc.domain.Category;
import org.mapstruct.MapperConfig;
import org.mapstruct.factory.Mappers;

@MapperConfig
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
