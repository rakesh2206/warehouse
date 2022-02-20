package com.assignment.warehouse.service;

import com.assignment.warehouse.domain.Article;
import com.assignment.warehouse.domain.Product;
import com.assignment.warehouse.domain.contain_articles;
import com.assignment.warehouse.repository.ArticleRepository;
import com.assignment.warehouse.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository vProductRepository;

    private ArticleRepository vArticleRepository;

    public ProductServiceImpl(ProductRepository vProductRepository, ArticleRepository pArticleRepository) {
        this.vProductRepository = vProductRepository;
        this.vArticleRepository = pArticleRepository;
    }


    public List<Product> getProducts() {
        List<Product> vProductInStockList = new ArrayList<>();

        //Get Article Stock Information from DB
        List<Article> vList2 = vArticleRepository.findAll();

        Iterable<Product> vProductList = vProductRepository.findAll();

        for (Product product : vProductList) {
            boolean articleExists = false;
            for (contain_articles vcontain_articles : product.getContain_articles()) {
                articleExists = isArticlewithStockAvailable(vList2, vcontain_articles);
                if (!articleExists) break;
            }
            if (articleExists) {
                vProductInStockList.add(product);
            }


     /*       List<contain_articles> vList1 = product.getContain_articles();
            List<contain_articles> vUpdatedList = vList2.stream()
                    .filter(ContainedArticle -> vList1.stream()
                            .allMatch(vArticlefromDB -> ContainedArticle.getArt_id() == vArticlefromDB.getArt_id()
                                    && vArticlefromDB.getStock() > ContainedArticle.getAmount_of())).collect(Collectors.toList());*/
        }
        return vProductInStockList;
    }

    private boolean isArticlewithStockAvailable(List<Article> pList2, contain_articles pcontain_articles) {
        for (Article vArticle : pList2) {
            if (pcontain_articles.getArt_id() == vArticle.getArt_id() &&
                    pcontain_articles.getAmount_of() <= vArticle.getStock()) {
                return true;
            }
        }
        return false;
    }

    public Product save(Product pProduct) {
        return vProductRepository.save(pProduct);
    }

    public Iterable<Product> save(List<Product> pProducts) {
        return vProductRepository.saveAll(pProducts);
    }

    public Product findById(Integer prod_id) {
        return vProductRepository.getById(prod_id);
    }


    public void  deleteById(Integer prod_id){

        // Find the product to see get the list of associated article
        // Find the Article Stock
        Optional<Product> vProducts = vProductRepository.findById(prod_id);
        List<contain_articles> pAssociatedArtList = vProducts.isPresent() ? vProducts.get().getContain_articles() : new ArrayList<contain_articles>();
        List<Article> vArticleInStockList =vArticleRepository.findAll();
        List<Article> vArticleTobeUpdatedList = vArticleInStockList.stream().filter(articleInStock -> pAssociatedArtList.stream().anyMatch(articleInProduct -> articleInProduct.getArt_id() == articleInStock.getArt_id())).collect(Collectors.toList());

        System.out.println(vArticleTobeUpdatedList);
        vArticleTobeUpdatedList.forEach(vArticleTobeUpdate -> {
            // find the first B object with matching art_id:
            Optional<contain_articles> matchingArticle = pAssociatedArtList.stream().filter(associatedArt -> Objects.equals(associatedArt.getArt_id(), vArticleTobeUpdate.getArt_id())).findFirst();
            // and use it to set the dob value in this A list element:
            if (matchingArticle.isPresent()) {
                vArticleTobeUpdate.setStock(vArticleTobeUpdate.getStock() - matchingArticle.get().getAmount_of());
            }
        });
        System.out.println(vArticleTobeUpdatedList);
       try{
           vProductRepository.deleteById(prod_id);
       } catch (Exception e){
           System.out.println("Unable to Delete Products " + e.getMessage());
           throw new RuntimeException("Error in deöetin the product");

        }
       // Update the stock
        vArticleRepository.saveAll(vArticleTobeUpdatedList);

    }


}
