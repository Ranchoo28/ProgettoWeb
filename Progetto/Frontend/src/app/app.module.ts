import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from '@angular/material/icon';


// Components
import { AppComponent } from './app.component';
import { NavbarComponent } from './componenti/navbar/navbar.component';


// Routing
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent } from './componenti/galleria/galleria.component';
import { ContattiComponent } from './componenti/contatti/contatti.component';
import { HomeComponent } from './componenti/home/home.component';
import { NotfoundComponent } from './componenti/notfound/notfound.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ProdottiComponent,
    PrenotaComponent,
    GalleriaComponent,
    ContattiComponent,
    HomeComponent,
    NotfoundComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
