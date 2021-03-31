package matt.project.spring5restmvc.services;

import matt.project.spring5restmvc.api.v1.mapper.VendorMapper;
import matt.project.spring5restmvc.api.v1.model.VendorDTO;
import matt.project.spring5restmvc.api.v1.model.VendorListDTO;
import matt.project.spring5restmvc.controllers.v1.VendorController;
import matt.project.spring5restmvc.domain.Vendor;
import matt.project.spring5restmvc.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
          .map(vendorMapper::vendorToVendorDTO)
          .map(vendorDTO -> {
              vendorDTO.setVendorUrl(getVendorUrl(id));
              return vendorDTO;
          })
          .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOS = vendorRepository
          .findAll()
          .stream()
          .map(vendor -> {
              VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
              vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
              return vendorDTO;
          })
          .collect(Collectors.toList());

        return new VendorListDTO(vendorDTOS);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendorToSave.setId(id);

        return saveAndReturnDTO(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id)
          .map(vendor -> {

              if (vendorDTO.getName() != null) {
                  vendor.setName(vendorDTO.getName());
              }

              return saveAndReturnDTO(vendor);
          })
          .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);

        returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnDto;
    }
}
