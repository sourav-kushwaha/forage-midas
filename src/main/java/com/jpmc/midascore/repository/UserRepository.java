// package com.jpmc.midascore.repository;

// import com.jpmc.midascore.entity.UserRecord;
// import org.springframework.data.repository.CrudRepository;

// public interface UserRepository extends CrudRepository<UserRecord, Long> {
//     UserRecord findById(long id);
// }

package com.jpmc.midascore.repository;

import org.springframework.data.repository.CrudRepository;

import com.jpmc.midascore.entity.UserRecord;

public interface UserRepository extends CrudRepository<UserRecord, Long> {
    // no custom methods needed for Task 3
   
}


