package com.qa.gorest.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	
		private Integer id;
		private String name;
		private String email;
		private String status;
		private String gender;
		
		public User(String name, String email, String status, String gender) {
			this.name = name;
			this.email = email;
			this.status = status;
			this.gender = gender;
		}

}
