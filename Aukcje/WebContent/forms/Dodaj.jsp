<div class="panel panel-info">
	<div class="panel-heading">
		<span class="glyphicon glyphicon-registration-mark"></span> Dodaj aukcję
	</div>
	<div class="panel-body">
		<form method="POST" enctype="multipart/form-data" class="fa navbar-form navbar-left form-singin form-horizontal"  novalidate>
			<input type="hidden" value="0" name="mode" id="mode"></input>
			
			<h4 class="col-sm-12">Informacje ogólne:</h4>
			
			<label class="control-label col-sm-4" for="nazwa_aukcji">Nazwa aukcji:</label>
			<div class="form-group col-sm-8">
				<input type="text" class="form-control" placeholder="Nazwa aukcji" name="nazwa_aukcji" value="%s" required="">
			</div>

			<label class="control-label col-sm-4" for="nazwa_aukcji">Data zakończenia:</label>
			<div class="form-group col-sm-8">
				<input style="background: white" type="text" class="form-control datepicker" readonly placeholder="Data zakończenia" name="data_zakonczenia" value="%s" required="">
			</div>

			<h4 class="col-sm-12">Informacje o przedmiocie:</h4>
			
			<label class="control-label col-sm-4" for="nazwa_przedmiotu">Nazwa przedmiotu:</label>
			<div class="form-group col-sm-8">
				<input type="text" class="form-control" placeholder="Nazwa przedmiotu" name="nazwa_przedmiotu" value="%s" required="">
			</div>
			
			<label class="control-label col-sm-4" for="opis">Opis przedmiotu:</label>
			<div class="form-group col-sm-8">
				<textarea class="form-control" placeholder="Opis przedmiotu" name="opis" required="">%s</textarea>
			</div>
			
			<label class="control-label col-sm-4" for="zdjecie">Zdjęcie:</label>
			
			<div class="form-group col-sm-8">
        		<input type="hidden"  name="mode" id="mode">
        	
           		 <input type="file" name="uploadFile" />
           		 <input type="submit"  value="Upload" />
           		 <br/><br/>
            </div>
           	
      		
			
			
			
			<br><br>
			<button  type="submit" class="btn btn-primary pull-right" ><span class="glyphicon glyphicon-plus"></span> Dodaj ogłoszenie</button>
		</form>
	</div>
</div>
