package service

import dto.*
import error.PasswordChangeException
import error.RegistrationException
import io.ktor.features.*
import model.UserModel
import org.springframework.security.crypto.password.PasswordEncoder
import repository.UserRepository

class UserService(
    private val repo: UserRepository,
    private val tokenService: JWTTokenService,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun getModelById(id: Int): UserModel? {
        return repo.getById(id)
    }

    suspend fun getById(id: Int): UserResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return UserResponseDto.fromModel(model)
    }

    suspend fun changePassword(id: Int, input: PasswordChangeRequestDto) {
        val model = repo.getById(id) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.old, model.password)) {
            throw PasswordChangeException("Wrong password!")
        }
        val copy = model.copy(password = passwordEncoder.encode(input.new))
        repo.save(copy)
    }

    suspend fun authenticate(input: AuthenticationRequestDto): AuthenticationResponseDto {
        val model = repo.getByUsername(input.userName) ?: throw NotFoundException()

        if (!passwordEncoder.matches(input.password, model.password)) {
            throw PasswordChangeException("Wrong password!")
        }
        val token = tokenService.generate(model.id)
        return AuthenticationResponseDto(token)
    }

    suspend fun saveNewModel(userName: String, password: String): UserModel {
        val model = UserModel(userName = userName, password = passwordEncoder.encode(password))
        return repo.save(model)
    }

    suspend fun register(input: RegistrationRequestDto): RegistrationResponseDto {
        if (repo.getByUsername(input.userName) != null) {
            throw RegistrationException()
        }

        val model = saveNewModel(input.userName, input.password)
        val token = tokenService.generate(model.id)
        return RegistrationResponseDto(token)
    }
}