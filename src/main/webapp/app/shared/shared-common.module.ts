import { NgModule } from '@angular/core';

import { DemoASharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [DemoASharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [DemoASharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DemoASharedCommonModule {}
