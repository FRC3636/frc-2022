{ 
  inputs = {
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in { 
        devShell = with pkgs; mkShell {
          buildInputs = [
            adoptopenjdk-openj9-bin-11
          ];
        };
      }
    );
}