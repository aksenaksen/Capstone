package com.example.capstone.user.security;

import com.example.capstone.user.jpa.UserEntity;
import com.example.capstone.user.jpa.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsersRepository usersRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final String NO_CHECK_URL="/api/users/login";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URL)){
            filterChain.doFilter(request,response);
            return;
        }

        String refreshToken = jwtService
                .extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
            return;
        }

        checkAccessTokenAndAuthentication(request,response,filterChain);
    }


    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("check access Token");
        jwtService.extractAccessToken(request).filter(jwtService::isTokenValid).ifPresent(

                accessToken -> jwtService.extractEmail(accessToken).ifPresent(

                        email -> usersRepository.findByEmail(email).ifPresent(

                                        users -> {saveAuthentication(users);
                                        log.info("success1");
                                        log.info("Authentication after saving: {}", SecurityContextHolder.getContext().getAuthentication());}


                        )
                )
        );

        filterChain.doFilter(request,response);
    }

    private void saveAuthentication(UserEntity users) {

        log.info("Saving authentication for user: {}", users.getEmail());

        UserDetailsImpl userDetails = new UserDetailsImpl(users);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);


        log.info("Authentication saved in SecurityContext for user: {}", users.getEmail());
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        log.info("hello");
        usersRepository.findByRefreshToken(refreshToken).ifPresent(
                users -> {jwtService.sendAccessToken(response, jwtService.createAccessToken(users.getEmail()));
                            log.info("새 accessToken이 발급되었습니다.");
                }
        );
    }
}