package matt.project.spring5restmvc.api.v1.mapper;

import matt.project.spring5restmvc.api.v1.model.VendorDTO;
import matt.project.spring5restmvc.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
