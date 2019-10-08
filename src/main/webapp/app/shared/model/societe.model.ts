export interface ISociete {
  id?: number;
  idsociete?: number;
  libelle?: string;
  adresse?: string;
}

export class Societe implements ISociete {
  constructor(public id?: number, public idsociete?: number, public libelle?: string, public adresse?: string) {}
}
