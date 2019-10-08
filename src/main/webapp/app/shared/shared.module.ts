import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DemoASharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DemoASharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DemoASharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoASharedModule {
  static forRoot() {
    return {
      ngModule: DemoASharedModule
    };
  }
}
