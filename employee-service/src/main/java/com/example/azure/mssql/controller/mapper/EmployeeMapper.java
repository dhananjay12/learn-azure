package com.example.azure.mssql.controller.mapper;

import com.example.azure.mssql.dto.EmployeeDTO;
import com.example.azure.mssql.persistence.EmployeeEntity;

public class EmployeeMapper {

    public static EmployeeEntity makeEntity(EmployeeDTO employeeDTO) {
        return new EmployeeEntity(employeeDTO.getId(), employeeDTO.getFname(),
            employeeDTO.getLname(), employeeDTO.getEmail(), employeeDTO.getDob());
    }

    public static EmployeeDTO makeDTO(EmployeeEntity entity) {
        EmployeeDTO.EmployeeDTOBuilder employeeDTOBuilder = EmployeeDTO.builder()
            .id(entity.getId()).fname(entity.getFname()).lname(entity.getLname())
            .email(entity.getEmail()).dob(entity.getDob());
        return employeeDTOBuilder.build();
    }

}
