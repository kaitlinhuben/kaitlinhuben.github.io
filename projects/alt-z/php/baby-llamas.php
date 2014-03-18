<?php require('php/header.php'); ?>
          <div class="jumbotron">
		  <img src="images/baby-llama-1.jpg" alt="This baby llama knows she's adorable." id="baby-image"/>
            <h2 class="green" id="baby-header">A baby llama is called a <em>cria</em>. They're adorable - and they know it!</h2>
            <p>Who wouldn't want to see a slideshow of baby llamas?<br />
            	They make everyone happier. <br /> </p>
          </div>
          <div class="row row-container">
		   <div class="row">
            <div class="col-12 col-sm-12 col-lg-12" id="galleria">

            <a href="images/baby-llama-2.jpg">
                <img 
                    src="images/baby-llama-2.jpg",
                    data-title="A very little baby llama"
                    data-description="From http://www.funnycutestuff.com/wp-content/uploads/2011/11/baby-llama.jpg"
                >
            </a>
			<a href="images/baby-llama-3.jpg">
                <img 
                    src="images/baby-llama-3.jpg",
                    data-title="Like llama like son"
                    data-description="From http://www.users.qwest.net/~herblibrarian/tcl/jpg/Thor.jpg"
                >
            </a>
			<a href="images/baby-llama-4.jpg">
                <img 
                    src="images/baby-llama-4.jpg",
                    data-title="Small llama and small human - who knew?"
                    data-description="From http://www.smilefunny.com/pictures/Baby_Llama_and_Child.jpg"
                >
            </a>
			<a href="images/baby-llama-5.jpg">
                <img 
                    src="images/baby-llama-5.jpg",
                    data-title="This one wants you to have a good day"
                    data-description="From http://todayspetpics.files.wordpress.com/2012/11/baby-llama.jpg"
                >
            </a>
			<a href="images/baby-llamas-6.jpg">
                <img 
                    src="images/baby-llamas-6.jpg",
                    data-title="So these are more like teenage llamas. They're still cute!"
                    data-description="From http://supercuter.com/wp-content/uploads/2011/10/baby-llamas.jpg"
                >
            </a>
			<a href="images/baby-llama-1.jpg">
                <img 
                    src="images/baby-llama-1.jpg",
                    data-title="What a happy llama"
                    data-description="From http://www.acuteaday.com/blog/wp-content/uploads/2011/02/baby-llama.jpg"
                >
            </a>
			</div><!--/span-->
              <br>
              <p><a class="btn btn-default" id="back-to-homepage" href="index.php">&laquo; Head back to the homepage</a></p>
            </div><!--/row-->
		   </div><!--/row-->
          </div><!--/row-->
        <?php require('php/quick-links.php'); ?>
	
	<!-- load jQuery and galleria for the image gallery -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>	
	<script src="galleria/galleria-1.2.9.js"></script>
	<script>

    // Load the classic theme
    Galleria.loadTheme('galleria/themes/classic/galleria.classic.min.js');

    // Initialize Galleria and autoplay with 5 seconds in between
    Galleria.run('#galleria', {autoplay: 5000});

    </script>
<?php require('php/footer.php'); ?>