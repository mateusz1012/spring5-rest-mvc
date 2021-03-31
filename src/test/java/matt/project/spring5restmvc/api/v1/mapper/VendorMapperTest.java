package matt.project.spring5restmvc.api.v1.mapper;

import matt.project.spring5restmvc.api.v1.model.VendorDTO;
import matt.project.spring5restmvc.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String NAME = "someName";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDtoToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}