package com.seismap.service.impl;

import com.seismap.model.entity.User;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.common.ActorCredentialsDto;

public abstract class AbstractServiceImpl {

	public enum Role {
		ANONYMOUS {
			@Override
			protected User getValidatedUser(UserRepository userRepository,
					ActorCredentialsDto actorCredentials) {
				try {
					return getUser(userRepository, actorCredentials);
				} catch (UnauthorizedException e) {
					return null;
				}
			}

			@Override
			protected void validateUser(UserRepository userRepository,
					ActorCredentialsDto actorCredentials)
					throws UnauthorizedException {
			}
		},

		REGULAR {
			@Override
			protected User getValidatedUser(UserRepository userRepository,
					ActorCredentialsDto actorCredentials)
					throws UnauthorizedException {
				return getUser(userRepository, actorCredentials);
			}
		},

		ADMIN {
			@Override
			protected User getValidatedUser(UserRepository userRepository,
					ActorCredentialsDto actorCredentials)
					throws UnauthorizedException {
				User user = getUser(userRepository, actorCredentials);
				if (!user.isAdministrator()) {
					throw new UnauthorizedException(
							"Se requiere privilegio de administrador.");
				}
				return user;
			}
		};

		protected abstract User getValidatedUser(UserRepository userRepository,
				ActorCredentialsDto actorCredentials)
				throws UnauthorizedException;

		protected void validateUser(UserRepository userRepository,
				ActorCredentialsDto actorCredentials)
				throws UnauthorizedException {
			getValidatedUser(userRepository, actorCredentials);
		}

		protected User getUser(UserRepository userRepository,
				ActorCredentialsDto actorCredentials)
				throws UnauthorizedException {
			if (actorCredentials == null
					|| actorCredentials.getUserId() == null
					|| actorCredentials.getPasswordHash() == null) {
				throw new UnauthorizedException(
						"Usuario o contraseña invalida.");
			}
			User user = userRepository.get(actorCredentials.getUserId());
			if (user == null

					|| !user.getPasswordHash().equals(
							actorCredentials.getPasswordHash())) {
				throw new UnauthorizedException(
						"Usuario o contraseña invalida.");
			}
			return user;
		}

	}

	private UserRepository userRepository;

	public AbstractServiceImpl() {
	}

	public AbstractServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	protected User getValidatedUser(ActorCredentialsDto actorCredentials,
			Role requiredRole) throws UnauthorizedException {
		return requiredRole.getValidatedUser(userRepository, actorCredentials);
	}

	protected void validateUser(ActorCredentialsDto actorCredentials,
			Role requiredRole) throws UnauthorizedException {
		requiredRole.validateUser(userRepository, actorCredentials);
	}

}
