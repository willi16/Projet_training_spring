export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  data: {
    token: string;
    username: string;
    role: string;
  };
  statusCode: number;
  description: string;
}

export interface Personnel {
  trackingId?: string;
  username: string;
  password?: string;
  nom: string;
  prenom: string;
  email: string;
  matricule: string;
  role: string;
  actif: boolean;
}

export interface Niveau {
  trackingId: string; // L'UUID (pour l'affichage et le tracking)
  libelle: string;
}

export interface Apprenant {
  trackingId?: string;
  matricule?: string;
  nom: string;
  prenom: string;
  dateNaissance: string;
  sexe: string;
  classeTrackingId: string;
  classeLibelle?: string;
  statutPaiement?: string;
  parent?: {
    trackingId?: string;
    nom: string;
    prenom: string;
    contact: string;
    email?: string;
    adresse?: string;
    profession?: string;
    lien: string;
  };
}

export interface Classe {
  trackingId?: string;
  libelle: string;
  capaciteTotal: number;
  niveauTrackingId: string;
  niveauLibelle?: string;
  effectif?: number;
}

export interface TypeFrais {
  trackingId?: string;
  code: string;
  libelle: string;
  montantReference: number;
  modeDecoupage: string; // TypeFraisCode
  reglesDecoupage?: string;
  cible?: string;
  classeLibelle?: string;
}

export interface Echeancier {
  trackingId: string;
  libelleTypeFrais: string;
  montantPrevu: number;
  montantPaye: number;
  resteAPayer: number;
  dateEcheance: string;
  statut: string; // StatutEcheance
}

export interface PaiementRequest {
  montantTotal: number;
  moyen: string; // MoyenPaiement
  refTransaction?: string;
  datePaiement: string;
  apprenantTrackingId: string;
  caissierTrackingId?: string; // Optionnel, le backend peut le déduire du token
}

export interface PaiementResponse {
  trackingId: string;
  numeroRecu: string;
  montantTotal: number;
  moyen: string;
  datePaiement: string;
  statut: string;
  matriculeApprenant: string;
  totalImpute: number;
  reliquat: number;
}


export interface RetardResponse {
  echeancierTrackingId: string;
  apprenantTrackingId: string;
  matriculeApprenant: string;
  nomApprenant: string;
  classeLibelle: string | null;
  libelleTypeFrais: string;
  dateEcheance: string;
  montantPrevu: number;
  montantPaye: number;
  resteAPayer: number;
  joursDeRetard: number;
  statut: string;
  parentNom: string | null;
  parentContact: string | null;
}