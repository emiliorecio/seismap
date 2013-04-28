package com.seismap.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.ApplicationSettings;
import com.seismap.model.repository.ApplicationRepository;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.application.ApplicationService;
import com.seismap.service.application.ApplicationSettingsDto;
import com.seismap.service.application.GetApplicationRequestDto;
import com.seismap.service.application.GetApplicationResponseDto;
import com.seismap.service.application.GetApplicationSettingsRequestDto;
import com.seismap.service.application.GetApplicationSettingsResponseDto;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;

public class ApplicationServiceImpl extends AbstractServiceImpl implements
		ApplicationService, BeanFactoryAware, InitializingBean, BeanNameAware {

	private static final long DEFAULT_APPLICATION_ID = 1;

	private long applicationId = DEFAULT_APPLICATION_ID;

	private ApplicationRepository applicationRepository;

	/* begin: tx aware self reference */
	private String beanName;
	private BeanFactory beanFactory;
	private ApplicationServiceImpl self;
	/* end: tx aware self reference */

	/* begin: settings cache */
	private long settingsCacheExpiration = 0;
	private ApplicationSettingsDto catchedSettings = null;
	private long catchedPrivateSettingsTimestamp = 0;

	/* end: settings cache */

	protected ApplicationServiceImpl() {
	}

	public ApplicationServiceImpl(ApplicationRepository applicationRepository,
			UserRepository userRepository) {
		super(userRepository);
		this.applicationRepository = applicationRepository;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	protected long getApplicationId() {
		return applicationId;
	}

	@Transactional
	public GetApplicationResponseDto get(ActorCredentialsDto actorCredentials,
			GetApplicationRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ADMIN);
		} catch (UnauthorizedException e) {
			return new GetApplicationResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		return new GetApplicationResponseDto(
				DtoMarshaler.unmarshallApplication(applicationRepository
						.fetchSingleton()));
	}

	@Transactional
	public ApplicationSettingsDto getSettings() {
		if (catchedSettings == null
				|| System.currentTimeMillis() - catchedPrivateSettingsTimestamp >= settingsCacheExpiration) {
			catchedPrivateSettingsTimestamp = System.currentTimeMillis();
			ApplicationSettings settings = applicationRepository
					.fetchSingleton().getApplicationSettings();
			settingsCacheExpiration = settings.getSettingsCacheExpiration();
			catchedSettings = DtoMarshaler
					.unmarshallApplicationSettings(settings);
		}
		return catchedSettings;
	}

	public GetApplicationSettingsResponseDto getSettings(
			ActorCredentialsDto actorCredentials,
			GetApplicationSettingsRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new GetApplicationSettingsResponseDto(
					ExceptionCause.UNAUTHORIZED, e.getMessage());
		}
		ApplicationSettingsDto settings = self.getSettings();
		return new GetApplicationSettingsResponseDto(settings);
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void afterPropertiesSet() throws Exception {
		self = beanFactory.getBean(beanName, ApplicationServiceImpl.class);
	}

}
