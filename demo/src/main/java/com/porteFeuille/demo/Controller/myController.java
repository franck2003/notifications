package com.porteFeuille.demo.Controller;

import com.porteFeuille.demo.Serveur.Entity.Entity_table.*;
import com.porteFeuille.demo.Serveur.Entity.Object.Adresse;
import com.porteFeuille.demo.Serveur.Repositories.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class myController implements WebMvcConfigurer {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ConsommateurRepositories consommateurRepositories;
    @Autowired
    private NotificationRepositories notificationRepositories;
    @Autowired
    private  LoginRepositories loginRepositories;

    @Autowired
    FournisseurRepositories fournisseurRepositories;
    @Autowired
    ConsommationRepositories consommationRepositories;

    @Autowired
    HabitationRepositories habitationRepositories;

    @Autowired
    FacturationRepositories facturationRepositories;

    @Autowired
    AccounteMentuelRepositories acount;

    private Adresse adresse;

   // public void addRessourceHandlers(ResourceHandlerRegistry registry){
     //   registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
   // }
    @RequestMapping("/SignIn")
    public String submitForm(){
        return "SignIn";
    }


    @GetMapping("/Style2.css")
    public ResponseEntity<Resource> getPageCss(){
        Resource resource = new ClassPathResource("/static/css/Style2.css");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/css")).body(resource);
    }

    @GetMapping("/bienvenu")
    public String bienvenu(@RequestParam(value = "username") String username,
                                           @RequestParam(value = "password") String password) {
        System.out.println(username);
        System.out.println(password);
        if (username == null || password == null) {
            System.out.println("monsieur");
            return "erreur";
        }
        Optional<Login> tmp = loginRepositories.findByEmail(username);
        if (tmp.isPresent()) {
            boolean bool = passwordEncoder.matches(password, tmp.get().getMotDePasse());
            if (bool){
                return "Home";
            }
        }
        System.out.println("aurevoir");
        return "error";
    }
    @PostMapping("/process_register")
    public String processRegister(@Validated Consommateur consommateur, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "error";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(consommateur.getMotDePasse());
        consommateur.setMotDePasse(password);
        System.out.println(consommateur.getEmail());

        loginRepositories.save(new Login(consommateur.getEmail(), consommateur.getMotDePasse()));
        consommateurRepositories.save(consommateur);

        return "register";
    }

    @PostMapping("/verification")
    public String verification(@Validated ResetPwd resetPwd, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "error";
        }
        System.out.println(Login.getWhoIsConnect());
        Optional<Login> tmp = loginRepositories.findById(Login.getWhoIsConnect());
        if(tmp.isPresent()){
            System.out.println("je suis entre");
            boolean bool = passwordEncoder.matches(resetPwd.getMotDePasse(), tmp.get().getMotDePasse());
            System.out.println(bool);
            if(bool && resetPwd.getNouveau_mdp().equals(resetPwd.getConfirm_mdp())){
                Login user = tmp.get();
                user.setMotDePasse(passwordEncoder.encode(resetPwd.getNouveau_mdp()));
                loginRepositories.save(user);
                return "Home";
            }
        }
        System.out.println("je suis sortir");
        return "error";
    }

    @PostMapping("/register")
    public String Register(@Validated Login login, BindingResult result, Model model) {
        Optional<Consommateur> tmp = consommateurRepositories.findByEmail(login.getEmail());
        if (tmp.isPresent()) {
            boolean bool = passwordEncoder.matches(login.getMotDePasse(), tmp.get().getMotDePasse());
            if (bool){
                Login.setWhoIsConnect(tmp.get().getConsommateurId());
                System.out.println("merci");
                return "Home";
            }
        }
        System.out.println("aurevoir");
        return "error";
    }
    @GetMapping("/SignUp")
    public String showRegistrationForm(Model model) {
        model.addAttribute("consommateur", new Consommateur());

        return "signUp";
    }

    @GetMapping("ChangePassword.html")
    public String ChangePassword(Model model){
        model.addAttribute("ResetPwd", new ResetPwd());
        return "ChangePassword";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("Home")
    public String getHome(){
        return "Home";
    }

    @GetMapping("/css/Home.css")
    public ResponseEntity<InputStreamResource> getHomeCSS() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("static/css/Home.css");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/css");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/css/Style3.css")
    public ResponseEntity<InputStreamResource> getS3CSS() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("static/css/Style3.css");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/css");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/css/Style2.css")
    public ResponseEntity<InputStreamResource> getS2CSS() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("static/css/Style2.css");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/css");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }
    @GetMapping(value = "/SettingsIcon.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSettingsIcon() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/image/SettingsIcon.png");

        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
    @GetMapping(value = "/NotifWhite.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getNotifWhitePNG() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/image/NotifWhite.png");

        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/notification.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getNotifPNG() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/image/notification.png");

        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
    @GetMapping(value = "/wallet.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getWallet5PNG() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/image/wallet.png");

        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


    @GetMapping("/calcul_facture")
    public String calculerFacture(@RequestParam("mois") String mois,
                                  @RequestParam("consommation") Long consommation,
                                  Model model) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<Date> dates = generatesDates();
        Optional<Fournisseur> f = fournisseurRepositories.findByFournisseur_id(2002L);
        PointFourniture pointFourniture = new PointFourniture(102L);
        Habitation habitation = new Habitation(11002L);
        Optional<Consommation> c = consommationRepositories.findByPointFournitureIdAndHabitationId(pointFourniture, habitation);
        Optional<Habitation> h = habitationRepositories.findByHabitationId(11002L);

        if(f.isPresent() && c.isPresent() && h.isPresent()){
            System.out.println("f = " + f);
            Fournisseur fournisseur = f.get();
            System.out.println("fournisseur = " + fournisseur);
            Long montant = fournisseur.getCout() * consommation;
            System.out.println("montant = " + montant);
            AccounteMentuel accounteMentuel = new AccounteMentuel(montant, dates.get(0),dates.get(1), "impayé");
            acount.save(accounteMentuel);

            Optional<AccounteMentuel> accounteMentuel1 = acount.findByMontantAndDate_facturationAndDate_echeanceAndStatus_paiement(accounteMentuel.getMontant(),accounteMentuel.getDate_facturation(), accounteMentuel.getDate_echeance(),accounteMentuel.getStatus_paiement());
            Facturation facturation = new Facturation(new Fournisseur(fournisseur.getFournisseur_id()),h.get().getConsommateur_id(),new Habitation(h.get().getHabitationId()),new AccounteMentuel(accounteMentuel1.get().getId()));
            if (!model.containsAttribute("consommation")) {
                model.addAttribute("consommation", consommation);
            }
            if (!model.containsAttribute("facturation")) {
                model.addAttribute("facture", montant);
            }

            facturationRepositories.save(facturation);
        }else {
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        }

        // Renvoie le nom de la vue à afficher pour afficher les résultats
        return "resultat";
    }


    @GetMapping("ConsuElec.html")
    public String ConsuElec(){
        return "ConsuElec";
    }

    @GetMapping("ConsuGaz.html")
    public String ConsuGaz(){
        return "ConConsuGazsuElec";
    }

    @GetMapping("ConsuWater.html")
    public String ConsuWater(){
        return "ConsuWater";
    }

    @GetMapping("CountWater.html")
    public String CountWater(){
        return "CountWater";
    }

    @GetMapping("CountGaz.html")
    public String CountGaz(){
        return "CountGaz";
    }

    @GetMapping("CountElec.html")
    public String CountElec(){
        return "CountElec";
    }
    ///css/wallet%20client1.jpg

    @GetMapping("Setting.html")
    public String Setting(){
        return "Setting";
    }

    @GetMapping("Notifications.html")
    public String Notifications(){
        return "Notifications";
    }

    @GetMapping("CreateWallet")
    public String createWallet(Model model){
        model.addAttribute("adresse", new Adresse());
        return "CreateWallet";
    }

    @GetMapping("Home.html")
    public String home(){
        return "Home";
    }
    @GetMapping("facture_eau")
    public String facture_eau(){
        return "facture_eau";
    }

    @GetMapping("ManageWallet")
    public String ManageWallet(){
        return "ManageWallet";
    }

    @GetMapping("ShowWallet")
    public String ShowWallet(){
        return "ShowWallet";
    }

    @GetMapping("ShowUsers")
    public String ShowUsers(){
        return "ShowUsers";
    }

    @GetMapping("ShowHabitations")
    public String ShowHabitations(){
        return "ShowHabitations";
    }

    @GetMapping("ShowSuppliers")
    public String ShowSuppliers(){
        return "ShowSuppliers";
    }

    @GetMapping("MyConsumption")
    public String MyConsumption(){
        return "MyConsumption";
    }

    @GetMapping("ModifierWallet")
    public String ModifierWallet(){
        return "ModifierWallet";
    }

    @GetMapping("/traitement")
    public String traiterFormulaire(@RequestParam("electricite_fournisseur") String fournisseurElec,
                                    @RequestParam("electricite_duree") String dureeElec,
                                    @RequestParam("eau_fournisseur") String fournisseurEau,
                                    @RequestParam("eau_duree") String dureeEau,
                                    @RequestParam("gaz_fournisseur") String fournisseurGaz,
                                    @RequestParam("eau_gaz") String gazDuree) {
        Optional<Fournisseur> fournisseurE = fournisseurRepositories.findByNom_fournisseur(fournisseurElec);
        Optional<Fournisseur> fournisseurEa = fournisseurRepositories.findByNom_fournisseur(fournisseurEau);
        Optional<Fournisseur> fournisseurg = fournisseurRepositories.findByNom_fournisseur(fournisseurGaz);
        Optional<Consommateur> consommateur = consommateurRepositories.findById(Login.getWhoIsConnect());

        if(fournisseurg.isPresent() && consommateur.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nom",consommateur.get().getNom());
            jsonObject.put("prenom",consommateur.get().getPrenom());
            jsonObject.put("email",consommateur.get().getEmail());
            jsonObject.put("dure du contrat", dureeElec);
            jsonObject.put("adresse", adresse);
            adresse = new Adresse();
            String json = jsonObject.toString();
            notificationRepositories.save(new Notification(new Fournisseur(fournisseurg.get().getFournisseur_id()),new Consommateur(consommateur.get().getConsommateurId())
                        ,new Date(), "pas traité", json));
        }

        if(fournisseurE.isPresent() && consommateur.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nom",consommateur.get().getNom());
            jsonObject.put("prenom",consommateur.get().getPrenom());
            jsonObject.put("email",consommateur.get().getEmail());
            jsonObject.put("dure du contrat", dureeElec);
            jsonObject.put("adresse", adresse);
            adresse = new Adresse();
            String json = jsonObject.toString();
            notificationRepositories.save(new Notification(new Fournisseur(fournisseurE.get().getFournisseur_id()),new Consommateur(consommateur.get().getConsommateurId())
                    ,new Date(), "pas traité", json));
        }

        if(fournisseurEa.isPresent() && consommateur.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nom",consommateur.get().getNom());
            jsonObject.put("prenom",consommateur.get().getPrenom());
            jsonObject.put("email",consommateur.get().getEmail());
            jsonObject.put("dure du contrat", dureeElec);
            jsonObject.put("adresse", adresse);
            adresse = new Adresse();
            String json = jsonObject.toString();
            notificationRepositories.save(new Notification(new Fournisseur(fournisseurEa.get().getFournisseur_id()),new Consommateur(consommateur.get().getConsommateurId())
                    ,new Date(), "pas traité", json));
        }

        return "Home";
    }

    @GetMapping("CreateWallet2.html")
    public String createWallet2(@Validated Adresse adresse, Model model){
        this.adresse = adresse;
        model.addAttribute("Adresse", new Adresse());
        return "CreateWallet2";
    }

    public List<Date> generatesDates(){
        List<Date> dates = new ArrayList<>();
        dates.add(new Date());
        Date dateFin = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        dateFin.setTime(calendar.getTimeInMillis());
        dates.add(dateFin);
        return dates;
    }

}
