import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoASharedModule } from 'app/shared';
import {
  SocieteComponent,
  SocieteDetailComponent,
  SocieteUpdateComponent,
  SocieteDeletePopupComponent,
  SocieteDeleteDialogComponent,
  societeRoute,
  societePopupRoute
} from './';

const ENTITY_STATES = [...societeRoute, ...societePopupRoute];

@NgModule({
  imports: [DemoASharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SocieteComponent,
    SocieteDetailComponent,
    SocieteUpdateComponent,
    SocieteDeleteDialogComponent,
    SocieteDeletePopupComponent
  ],
  entryComponents: [SocieteComponent, SocieteUpdateComponent, SocieteDeleteDialogComponent, SocieteDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoASocieteModule {}
