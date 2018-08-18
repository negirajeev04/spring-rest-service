package org.rajnegi.spring.rest.webservices.springrestservice.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.rajnegi.spring.rest.webservices.springrestservice.bean.Post;
import org.rajnegi.spring.rest.webservices.springrestservice.bean.User;
import org.rajnegi.spring.rest.webservices.springrestservice.exceptions.UserNotFoundException;
import org.rajnegi.spring.rest.webservices.springrestservice.repository.PostResourceRepository;
import org.rajnegi.spring.rest.webservices.springrestservice.repository.UserResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	
	private UserResourceRepository userRepo;
	private PostResourceRepository postRepo;
	
	@Autowired
	public void setUserRepo(UserResourceRepository userRepo) {
		this.userRepo = userRepo;
	}
	@Autowired
	public void setPostRepo(PostResourceRepository postRepo) {
		this.postRepo = postRepo;
	}

	@GetMapping(value="/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepo.findAll();
	}
	
	@GetMapping(value="/jpa/users/{userid}")
	public Resource<User> retriveUser(@PathVariable int userid) {
		Optional<User> user = userRepo.findById(userid);

		if(!user.isPresent())
			throw new UserNotFoundException("userId-"+userid);
		//HATEOAS
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		Resource<User> userResource = new Resource<>(user.get(),linkTo.withRel("all-users"));
		
		return userResource;
	}
	
	@PostMapping(value="/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		@Valid
		User savedUser = userRepo.save(user);
		//Created /users/savedUser.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
												  .path(savedUser.getId().toString())
												  .build()
												  .toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(value = "/jpa/users/{userid}")
	public void deleteUser(@PathVariable int userid) {
		userRepo.deleteById(userid);
	}
	
	@GetMapping(value = "/jpa/users/{userId}/posts")
	public List<Post> retrieveAllPostForUser(@PathVariable int userId){
		Optional<User> user = userRepo.findById(userId);
		
		if(!user.isPresent())
			throw new UserNotFoundException("userId-"+userId);
		
		return user.get().getPosts();
		
	}
	@PostMapping(value = "/jpa/users/{userId}/posts")
	public ResponseEntity<Object> createPost(@Valid @RequestBody Post post, @PathVariable int userId) {

		Optional<User> user = userRepo.findById(userId);
		
		if(!user.isPresent())
			throw new UserNotFoundException("userId-"+userId);
		
		post.setUser(user.get());
		Post savedPost = postRepo.save(post);
		//Created
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
												  .path("/{id}")
												  .buildAndExpand(savedPost.getId())
												  .toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value = "/jpa/users/{userId}/posts/{postId}")
	public Resource<Post> retrievePost(@PathVariable int userId, @PathVariable int postId) {
		
		Optional<User> userOptional = userRepo.findById(userId);
		
		if(!userOptional.isPresent())
			throw new UserNotFoundException("userId-"+userId);
		
		Post returnPost = null;
		for(Post post : userOptional.get().getPosts()) {
			if(postId == post.getId()) {
				returnPost = post;
				break;
			}
		}
		
		if(returnPost == null)
			throw new PostNotFoundException("postId-"+postId);
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllPostForUser(userId));
		Resource<Post> postResource = new Resource<>(returnPost, linkTo.withRel("all-posts"));
		return postResource;
	}
	
}
