package matt.project.spring5restmvc.services;

import matt.project.spring5restmvc.api.v1.mapper.CustomerMapper;
import matt.project.spring5restmvc.api.v1.model.CustomerDTO;
import matt.project.spring5restmvc.domain.Customer;
import matt.project.spring5restmvc.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository
          .findAll()
          .stream()
          .map(customer -> {
              CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
              customerDTO.setCustomerUrl("/api/v1/customer/" + customer.getId());
              return customerDTO;
          })
          .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository.findById(id)
          .map(customerMapper::customerToCustomerDTO)
          .map(customerDTO -> {
              //set API URL
              customerDTO.setCustomerUrl("/api/v1/customer/" + id);
              return customerDTO;
          })
          .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }

            if (customerDTO.getLastname() != null) {
                customer.setLastname(customer.getLastname());
            }

            CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnDto.setCustomerUrl("/api/v1/customer/" + id);

            return returnDto;
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
