package com.example.lab6.Controller;

import com.example.lab6.Api.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee>employees=new ArrayList<>();
    @GetMapping("/get")
 public ResponseEntity<?>getEmployee(){
     return ResponseEntity.status(200).body(employees);
 }

 @PostMapping("/add")
 public ResponseEntity<?>addEmployee(@Valid @RequestBody Employee employee, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee has been added "));
 }

 @PutMapping("/update/{index}")
 public ResponseEntity<?>updatedEmployee(@PathVariable int index,@Valid @RequestBody Employee employee,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if(index<0||index>=employees.size()){
            return ResponseEntity.status(400).body(new ApiResponse("Out Of Bound"));
        }
        employees.set(index,employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee has been updatted "));
 }


 @DeleteMapping("/delete/{index}")
 public ResponseEntity<?>deleteEmployee(@PathVariable int index ){
        if(index<0||index>=employees.size()){
            return ResponseEntity.status(404).body(new ApiResponse("Employee Not Found "));
        }
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Employee has been delete it "));
 }


@GetMapping("/getempoly/{position}")
 public ResponseEntity<?>getEmployeeByPosition(@PathVariable String position){
        ArrayList<Employee>result=new ArrayList<>();
        if(position==null||!position.trim().matches("(?i)^(supervisor|coordinator)$")){
           return ResponseEntity.status(400).body(new ApiResponse("invalid position, must be supervisor or coordinator"));
        }
        String positionTrim=position.trim();
        for(Employee e:employees){
            if(e.getPosition().equalsIgnoreCase(positionTrim)){
                result.add(e);
            }
        }
        return ResponseEntity.status(200).body(result);

 }

 @GetMapping("/get/{minAge}/{maxAge}")
 public ResponseEntity<?>ageRange(@PathVariable Integer minAge,@PathVariable Integer maxAge){
if(minAge==null|| maxAge==null || maxAge<0 || minAge<0 ||minAge<=25|| maxAge>=90 || minAge>maxAge){
    return ResponseEntity.status(400).body(new ApiResponse("Not Valid ageRange"));
}

ArrayList<Employee>result=new ArrayList<>();
for(Employee e:employees){
    if(e.getAge()>=minAge && e.getAge()<=maxAge){
        result.add(e);
    }
}
return ResponseEntity.status(200).body(result);
 }

@PutMapping("/applyleave/{id}")
 public ResponseEntity<?>applayForannual(@PathVariable String id ){
     if(id==null||id.trim().length()<=2){
         return ResponseEntity.status(400).body(new ApiResponse("not valid id"));
     }
     String idTrim=id.trim();
     for(Employee e:employees){
         if(e.getId().equals(idTrim)){
             if(e.isOnLeave()){
                 return ResponseEntity.status(400).body(new ApiResponse("he is on leave"));
             }
             if(e.getAnnualLeave()<=0){
                 return ResponseEntity.status(400).body(new ApiResponse("the employee has zero annual leave"));
             }
             e.setOnLeave(true);
             e.setAnnualLeave(e.getAnnualLeave()-1);
             return ResponseEntity.status(200).body(new ApiResponse("Apply annual leave for employee successfully"));
         }

     }

     return ResponseEntity.status(404).body(new ApiResponse("Employee Not Found"));

 }

@GetMapping("/getnoannualleave")
 public ResponseEntity<?>getEmployeeNoAnnualLeave(){
        ArrayList<Employee>result=new ArrayList<>();
   for(Employee p:employees){
       if(p.getAnnualLeave()==0){
           result.add(p);
       }
   }
   return ResponseEntity.status(200).body(result);
 }


 @PutMapping("/updatepro/{id}/{requesterId}")
 public ResponseEntity<?>promotrEmpolyee(@PathVariable String id,@PathVariable String requesterId){
        if(id==null||id.trim().length()<=2){
            return ResponseEntity.status(400).body(new ApiResponse("not valid id "));
        }
     if (requesterId==null || requesterId.trim().length() <= 2) {
         return ResponseEntity.status(400).body(new ApiResponse("not valid requester id "));
     }
     boolean requesterIsSupervisor = false;
     for (Employee e : employees) {
         if (e.getId().equals(requesterId.trim()) && e.getPosition().equalsIgnoreCase("supervisor")) {
             requesterIsSupervisor = true;
             break;
         }
     }
     if (!requesterIsSupervisor) {
         return ResponseEntity.status(403).body(new ApiResponse("requester must be a supervisor"));
     }
     String idTrim=id.trim();
        for(Employee e:employees) {
            if (e.getId().equals(idTrim)) {
                if (e.getAge() < 30) {
                    return ResponseEntity.status(400).body(new ApiResponse("it is not at least 30"));
                }
                if (e.isOnLeave()) {
                    return ResponseEntity.status(400).body(new ApiResponse("employee is on a leave"));
                }
                e.setPosition("supervisor");
                return ResponseEntity.status(200).body(new ApiResponse("employee has promote"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("the Employee Not Found "));
 }















































}
