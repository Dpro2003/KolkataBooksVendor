package com.example.AmarBoiPora.repository;

import com.example.AmarBoiPora.entity.Book;
import com.example.AmarBoiPora.enums.BookCondition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    /* ==========================================================
                        VENDOR
       ========================================================== */

    // Get all active books of a vendor
    List<Book> findAllByVendorIdAndDeletedFalse(String vendorId);

    // Get a specific active book of a vendor
    Optional<Book> findByIdAndVendorIdAndDeletedFalse(String id, String vendorId);

    // Check duplicate ISBN for the same vendor
    boolean existsByIsbnAndVendorIdAndDeletedFalse(String isbn, String vendorId);

    // Count vendor active books
    long countByVendorIdAndDeletedFalse(String vendorId);


    /* ==========================================================
                        CUSTOMER
       ========================================================== */

    // All visible books
    List<Book> findAllByDeletedFalse();

    // Single book
    Optional<Book> findByIdAndDeletedFalse(String id);

    // Search
    List<Book> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

    List<Book> findByAuthorNameContainingIgnoreCaseAndDeletedFalse(String author);

    List<Book> findByPublicationContainingIgnoreCaseAndDeletedFalse(String publication);

    List<Book> findByCategoryAndDeletedFalse(BookCondition category);

    List<Book> findByLanguageAndDeletedFalse(String language);

    // Best Sellers
    List<Book> findByBestSellerTrueAndDeletedFalse();


    /* ==========================================================
                        ADMIN
       ========================================================== */

    // All books
    List<Book> findAll();

    // Soft Deleted Books
    List<Book> findAllByDeletedTrue();

    Optional<Book> findByIdAndDeletedTrue(String id);

    // Vendor profile
    List<Book> findAllByVendorId(String vendorId);

    // Dashboard Counts
    long countByDeletedFalse();

    long countByDeletedTrue();

    long countByBestSellerTrue();

    long countByVendorId(String vendorId);


    /* ==========================================================
                        ANALYTICS
       ========================================================== */

    // Top selling books
    List<Book> findTop10ByDeletedFalseOrderByUnitsSoldDesc();

    // Top selling books of a vendor
    List<Book> findTop10ByVendorIdAndDeletedFalseOrderByUnitsSoldDesc(String vendorId);

}
