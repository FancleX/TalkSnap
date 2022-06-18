<template>
  <footer class="font-small">
      <div class="container-fluid">
        <div class="row">
          <!-- Copyright -->
          <div class="footer-copyright py-4 col-sm-12 col-lg-3">
            © 2022 Copyright:
            <router-link to="/"> TalkSnap.com</router-link>
          </div>
          <div class="col-sm-12 col-lg-9 socialWrapper">
            <a href="https://facebook.com" target="_blank">
              <i class="fa fa-facebook-square fa-3x" aria-hidden="true"></i
            ></a>
            <a href="https://google.com" target="_blank">
              <i class="fa fa-google-plus-square fa-3x" aria-hidden="true"></i
            ></a>
            <a href="https://instagram.com" target="_blank">
              <i class="fa fa-instagram fa-3x" aria-hidden="true"></i
            ></a>
          </div>
        </div>
      </div>
    </footer>
</template>

<style scoped>
footer {
  position: relative;
  bottom: 0;
  width: 100%;
  height: 100%;
  margin-top: auto;
  border-top: 2px solid rgba(44, 44, 46, 0.1);
}
.footer-copyright {
  padding-left: 3rem;
}
.socialWrapper {
  margin: auto;
  padding-left: 3rem;
}
.fa {
  margin: 10px 3px 10px 10px;
}
.fa-facebook-square {
  color: #3b5998;
}
.fa-google-plus-square {
  color: #c32f10;
}
.fa-instagram {
  color: #ffffff;
}
</style>