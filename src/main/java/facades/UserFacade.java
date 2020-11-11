package facades;

import dto.CatDTO;
import entities.Cat;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    
    public List<CatDTO> getCats(String username){
        List<CatDTO> list = new ArrayList();
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u where u.userName = :username", User.class);
        query.setParameter("username", username);
        User user = query.getSingleResult();
        List<Cat> cats = user.getCats();
        for (Cat cat : cats) {
            list.add(new CatDTO(cat));
        }
        
        return list;
    }
    
    public CatDTO addCat(CatDTO catDTO, String username){
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<User> query = em.createQuery("select u from User u where u.userName = :username", User.class);
        query.setParameter("username", username);
        User user = query.getSingleResult();
        Cat cat = new Cat(catDTO.getName(), catDTO.getRace());
        user.addCat(cat);
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        CatDTO retCat = new CatDTO(cat);
        return retCat;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

}
