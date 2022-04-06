package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.FavoritesDto;
import com.example.footballbootswebapiis.dto.FavoritesResponseForGet;
import com.example.footballbootswebapiis.dto.FootballBootsDefaultSizeResponse;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.FavoritesMapper;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.Favorites;
import com.example.footballbootswebapiis.model.FootballBoots;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.repository.FavoritesRepository;
import com.example.footballbootswebapiis.repository.FootballBootsRepository;
import com.example.footballbootswebapiis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.LimitExceededException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.footballbootswebapiis.constants.Constants.MAX_LIMIT_OF_FAVORITES_PER_USER;

@Service
@Slf4j
public class FavoritesService implements FavoritesServiceApi {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final FootballBootsRepository footballBootsRepository;

    public FavoritesService(final FavoritesRepository favoritesRepository, final UserRepository userRepository, final FootballBootsRepository footballBootsRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
        this.footballBootsRepository = footballBootsRepository;
    }

    @Override
    @Transactional
    public FavoritesDto create(final FavoritesDto favoritesDto) throws LimitExceededException {
        Optional<User> optionalUser = this.userRepository.findById(favoritesDto.getUserId());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User doesn't exist!"));
        if(favoritesRepository.countByUserId(user.getId()) >= MAX_LIMIT_OF_FAVORITES_PER_USER)
        {
            throw new LimitExceededException("Can't add more favorites.");
        }
        return FavoritesMapper.mapFromModelToResponse(this.favoritesRepository.save(new Favorites(favoritesDto.getBootsId(), user)));
    }

    @Override
    public boolean isFavorite(int userId, int bootsId) {
        Optional<Favorites> optionalFavorites = this.favoritesRepository.getByUserIdAndBootsId(userId, bootsId);
        return optionalFavorites.isPresent();
    }

    @Override
    public List<FavoritesResponseForGet> getAllFavoritesForUserId(int userId) {
        List<FavoritesResponseForGet> favoritesResponseForGets = new ArrayList<>();
        List<Favorites> favorites = this.favoritesRepository.findAllByUserId(userId);
        for (Favorites favorites1 : favorites) {
            Optional<FootballBoots> footballBoots = this.footballBootsRepository.findById(favorites1.getBootsId());
            if (footballBoots.isPresent()) {
                FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse = FootballBootsMapper.mapFromModelToDefaultResponse(footballBoots.get());
                favoritesResponseForGets.add(new FavoritesResponseForGet(favorites1.getIdFavorites(), footballBootsDefaultSizeResponse.getId(), footballBootsDefaultSizeResponse.getName()));
            }
        }

        log.info("Favorites computed for user id {}. Data {}", userId, favoritesResponseForGets);
        return favoritesResponseForGets;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        this.favoritesRepository.deleteById(id);
    }


}
