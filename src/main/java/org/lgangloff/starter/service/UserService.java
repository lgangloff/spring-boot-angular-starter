package org.lgangloff.starter.service;

import java.util.Optional;

import org.apache.commons.text.RandomStringGenerator;
import org.joda.time.LocalDate;
import org.lgangloff.starter.config.Constants;
import org.lgangloff.starter.domain.User;
import org.lgangloff.starter.exception.EmailAlreadyUseException;
import org.lgangloff.starter.repository.AuthorityRepository;
import org.lgangloff.starter.repository.PersistentTokenRepository;
import org.lgangloff.starter.repository.UserRepository;
import org.lgangloff.starter.security.SecurityUtils;
import org.lgangloff.starter.web.rest.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private static final RandomStringGenerator PASSWORD_GENERATOR = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

	private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
  
    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = new LocalDate();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).stream().forEach(token ->{
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }
    
    
    public User doSave(UserDTO memberdto) throws EmailAlreadyUseException {
    	
    	User user = Optional.ofNullable(memberdto.getId())
    			.map(id -> userRepository.findOne(memberdto.getId()))
    			.orElse(new User());
    	
		user.getAuthorities().clear();
		user.getAuthorities().addAll(authorityRepository.findAllById(memberdto.getRoles()));

		Optional<User> findOneByLogin = userRepository.findOneByLogin(memberdto.getEmail());
		
		if (findOneByLogin.isPresent() && !findOneByLogin.equals(Optional.of(user))){
			throw new EmailAlreadyUseException(memberdto.getEmail());
		}
		
		user.setTitle(memberdto.getTitle());
		user.setFirstName(memberdto.getFirstName());
		user.setLastName(memberdto.getLastName());
		user.setNickName(memberdto.getNickName());
		user.setAddress(memberdto.getAddress());
		user.setZipCode(memberdto.getZipCode());
		user.setCity(memberdto.getCity());
		user.setLangKey(memberdto.getLangKey());
		user.setTelephonNumber(memberdto.getTelephonNumber());

		//L'email a changé ? on repasse par une validation d'email
		if (!memberdto.getEmail().equals(user.getLogin())){
			user.setLogin(memberdto.getEmail().toLowerCase());
			initAccountAndSendMail(user);
		}
		
		user = userRepository.save(user);
		return user;
	}

	public void initAccountAndSendMail(User member) {
		String generatePassword = PASSWORD_GENERATOR.generate(Constants.PASSWORD_SIZE);
		member.setLogin(member.getLogin().toLowerCase());
		member.setPassword(passwordEncoder.encode(generatePassword));
		member.setEnabled(false);
		member.setLocked(false);
		
		/*
		 * TODO:
		 * mailService.sendPasswordEmail(member, generatePassword);
		 */
		userRepository.save(member);
	}
	
	
   public void changePassword(String password) {
	   Optional.of(SecurityUtils.getCurrentUser()).ifPresent(u-> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }
   
   public boolean isValidPassword(String password){
       return Optional.of(SecurityUtils.getCurrentUser()).map(u-> {
           return passwordEncoder.matches(password, u.getPassword());
       }).orElse(false);
   }


	public void updateAccount(UserDTO dto) {
		
		User online = SecurityUtils.getCurrentUser();
		
		if(online == null){
			return;
		}

		online.setTitle(dto.getTitle());
		online.setFirstName(dto.getFirstName());
		online.setLastName(dto.getLastName());
		online.setAddress(dto.getAddress());
		online.setZipCode(dto.getZipCode());
		online.setCity(dto.getCity());
		online.setTelephonNumber(dto.getTelephonNumber());
		
		userRepository.save(online);
	}


	public Page<User> findAll(String query, Pageable pageable) {
		return userRepository.findAll(query, pageable);
	}


	public User findOne(Long id) {
		return userRepository.findOne(id);
	}


	public void deleteUser(Long id) {		
		userRepository.deleteById(id);
	}


	public void lockUser(User user) {
		user.setEnabled(false);
		user.setLocked(true);
		userRepository.save(user);
	}


}
