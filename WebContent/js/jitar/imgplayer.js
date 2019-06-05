ImagePlayer = !!window.ImagePlayer || {};
ImagePlayer.slide = function (src,link,title,target,attr,desc,username,createdate) {
  this.desc = desc
  this.src = src;
  this.link = link;
  this.title = title;
  this.target = target;

  this.username = username;
  this.createdate = createdate;
  
  
  this.attr = attr;
  if (document.images) {
    this.image = new Image();
  }
  this.loaded = false;
  this.load = function() {
    if (!document.images) { return; }

    if (!this.loaded) {
      this.image.src = this.src;
      //alert(this.image.src + " , " + this.image.width)
      this.loaded = true;
    }
  }
  this.hotlink = function() {
    var mywindow;
    if (!this.link) return;
    if (this.target) {
      if (this.attr) {
        mywindow = window.open(this.link, this.target, this.attr);
  
      } else {
        mywindow = window.open(this.link, this.target);
      }
      if (mywindow && mywindow.focus) mywindow.focus();

    } else {
      location.href = this.link;
    }
  }
}

ImagePlayer.slideshow = function( slideshowname ) {		
  this.name = slideshowname;
  this.repeat = true;
  this.prefetch = -1;
  this.image;
  this.textid;
  this.timeout = 5000;
  this.slides = new Array();
  this.current = 0;
  this.timeoutid = 0;
  this.add_slide = function(slide) {
    var i = this.slides.length;
    if (this.prefetch == -1) {
      slide.load();
    }

    this.slides[i] = slide;
  }
  
  this.play = function(timeout) {
    this.pause();
    this.update();
    if (timeout) {
      this.timeout = timeout;
    }
    if (typeof this.slides[ this.current ].timeout != 'undefined') {
      timeout = this.slides[ this.current ].timeout;
    } else {
      timeout = this.timeout;
    }
    this.timeoutid = setTimeout( this.name + ".loop()", timeout);
  }
  
  this.pause = function() {
    if (this.timeoutid != 0) {
      clearTimeout(this.timeoutid);
      this.timeoutid = 0;
    }
  }
    
  this.loop = function() {
    if (this.current < this.slides.length - 1) {
      next_slide = this.slides[this.current + 1];
      if (next_slide.image.complete == null || next_slide.image.complete) {
        this.next();
      }
    } else {
      this.next();
    }
    this.play( );
  }
  
   this.next = function() {
    if (this.current < this.slides.length - 1) {
      this.current++;
    } else if (this.repeat) {
      this.current = 0;
    }
    this.update();
  }
  this.previous = function() {
    if (this.current > 0) {
      this.current--;
    } else if (this.repeat) {
      this.current = this.slides.length - 1;
    }
    this.update();
  }
    
  
  this.goSlide = function(slideItem) {
  	this.current = slideItem;
   	this.pause()
    this.play()
  }
  
  this.update = function() {
    $('LinkHref1').href=$('LinkHref2').href = this.slides[this.current].link;
    $('LinkHref2').innerHTML = this.slides[this.current].title;
    $('ImgSrc').src = this.slides[this.current].image.src;
    //alert(this.slides[this.current].image.width > 600)    
    CommonUtil.reFixImg(this.slides[this.current].image,600,400)   
    $('sub_title').innerHTML = '上传者：' + this.slides[this.current].username + ' 上传日期：' + this.slides[this.current].createdate;
    
    for(i = 0;i<$('ImgNav').getElementsByTagName('DIV').length;i++)
    {
    	if(i == this.current)
    	$('ImgNav').getElementsByTagName('DIV')[i].className = 'itemOn'; 
    	else
    	$('ImgNav').getElementsByTagName('DIV')[i].className = 'itemOff';    	
    }
    
  }
}

